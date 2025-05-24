package com.user.managament.config;

import com.user.managament.services.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ContractExpirationScheduler {
    private static final Logger log = LoggerFactory.getLogger(ContractExpirationScheduler.class);

    @Autowired
    private ContractService contractService;

    /**
     * Executa a verificação de contratos expirados todos os dias à 1:05 AM (UTC).
     *
     * A expressão CRON segue o formato: segundo minuto hora dia-do-mês mês dia-da-semana
     * "0 5 1 * * ?" significa:
     * - 0: no segundo 0
     * - 5: no minuto 5
     * - 1: na hora 1 (1 AM)
     * - *: todos os dias do mês
     * - *: todos os meses
     * - ?: qualquer dia da semana
     *
     */
    @Scheduled(cron = "0 5 1 * * ?", zone = "UTC")
    public void scheduleContractExpirationCheck() {
        log.info("Tarefa agendada 'scheduleContractExpirationCheck' iniciada.");
        try {
            contractService.expireDueContracts();
        } catch (Exception e) {
            log.error("Erro GERAL durante a execução da tarefa agendada 'scheduleContractExpirationCheck'.", e);
        }
        log.info("Tarefa agendada 'scheduleContractExpirationCheck' concluída.");
    }

}
