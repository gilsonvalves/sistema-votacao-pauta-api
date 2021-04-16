package io.github.gilsonvalve.pautaVotacao.utils;

import io.github.gilsonvalve.pautaVotacao.exception.ResourseErroException;
import io.github.gilsonvalve.pautaVotacao.jms.JmsConfig;
import io.github.gilsonvalve.pautaVotacao.model.Votacao;
import io.github.gilsonvalve.pautaVotacao.model.VotacaoStatus;
import io.github.gilsonvalve.pautaVotacao.repository.VotacaoRespository;
import io.github.gilsonvalve.pautaVotacao.repository.VotacaoStatusRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TarefasAgendadas {

    @Autowired
    VotacaoRespository votacaoRespository;

    @Autowired
    VotacaoStatusRespository votacaoStatusRespository;

    @Autowired
    JmsTemplate jmsTemplate;

    public  void agedanemntoExcutando (Integer id, long minutes){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(new VotacaoEndTask(id),  minutes, TimeUnit.MINUTES);
    }
    class VotacaoEndTask implements Runnable{
        private final Integer id;

        public VotacaoEndTask(Integer id) {
            this.id = id;
        }
        @Override
        public void run() {
            Optional<Votacao> votacao = votacaoRespository.findById(id);
            try {
                if (!votacao.isPresent())
                    throw new ResourseErroException("Votação ID: " + id + " não encontrada!");

                votacao.get().setOpen(false);
                votacao.get().setInicioVotacao(new Date(System.currentTimeMillis()));
                votacaoRespository.save(votacao.get());

                Optional<VotacaoStatus> votacaoStatus = votacaoStatusRespository.votacaoContabilizada(id);

                StringBuilder mensagemStatus = new StringBuilder();
                if(votacaoStatus.isPresent() && votacaoStatus.get().getVotoQuant() > 0) {
                    double porcentagemSim = (votacaoStatus.get().getSimQuant() * 100) / votacaoStatus.get().getVotoQuant();
                    double porcentagemNao = (votacaoStatus.get().getNaoQuant() * 100) / votacaoStatus.get().getVotoQuant();

                    if(votacaoStatus.get().getSimQuant() > votacaoStatus.get().getNaoQuant())
                        mensagemStatus.append("A pauta ID: ").append(votacao.get().getPatua().getId())
                                .append(" foi aprovada! ").append(porcentagemSim)
                                .append("% dos votos favoráveis.");
                    else if(votacaoStatus.get().getSimQuant() < votacaoStatus.get().getNaoQuant())
                        mensagemStatus.append("A pauta ID: ").append(votacao.get().getPatua().getId())
                                .append(" foi reprovada! ").append(porcentagemNao)
                                .append("% dos votos contrários.");
                    else
                        mensagemStatus.append("A pauta ID: ").append(votacao.get().getPatua().getId())
                                .append(" foi votada com empate nos votos, mas sem uma conclusão de aprovação! ")
                                .append(porcentagemSim).append("% dos votos favoráveis e ")
                                .append(porcentagemNao).append("% dos votos contrários.");
                } else {
                    mensagemStatus.append("A pauta ID: ").append(votacao.get().getPatua().getId())
                            .append(" não teve votos!");
                }

                votacaoStatus.get().setResultado(mensagemStatus.toString());
                VotacaoStatus votacaoStatusSaved = votacaoStatusRespository.save(votacaoStatus.get());

                jmsTemplate.convertAndSend("queue.sample", votacaoStatusSaved);
            } catch(ResourseErroException e) {
                e.printStackTrace();
            }
        }


    }
}
