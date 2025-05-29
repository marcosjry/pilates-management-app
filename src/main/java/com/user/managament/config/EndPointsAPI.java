package com.user.managament.config;

public class EndPointsAPI {
    private EndPointsAPI() {
        throw new UnsupportedOperationException("Classe utilitária.");
    }

    public static final String USER = "/api/usuario";

    public static final String AUTH = "/api/auth";
    public static final String AUTH_LOGIN = "/api/auth/login";
    public static final String AUTH_DENIED = "/api/auth/access-error";

    public static final String CUSTOMER = "/api/aluno";
    public static final String CUSTOMERS_AVAILABLE = "/api/aluno/available";
    public static final String CUSTOMER_SEARCH = "/api/aluno/search";
    public static final String CUSTOMER_FILTER = "/api/aluno/filter";
    public static final String CUSTOMER_ID = "/api/aluno/{customerId}";

    public static final String CONTRACT = "/api/contrato";
    public static final String CONTRACT_SEARCH = "/api/contrato/search";
    public static final String CONTRACT_TOTALS = "/api/contrato/totals";
    public static final String CONTRACTS_OF_USER = "/api/contrato/{customerId}";
    public static final String CONTRACT_ID = "/api/contrato/{contractId}";
    public static final String CONTRACT_LAST_BY_USER = "/api/contrato/last/{customerId}";
    public static final String CONTRACT_LAST_INFO_USER = "/api/contrato/info-user/{customerId}";
    public static final String CONTRACTS_EXPIRING = "/api/contrato/expiring-contracts";

    public static final String CLASSROOM = "/api/turma";
    public static final String CLASSROOM_HOURS_AVAILABLE = "/api/turma/hours-available";
    public static final String CLASSROOM_TODAY_CLASSES = "/api/turma/today-classes";
    public static final String CLASSROOM_ID = "/api/turma/{id}";

    public static final String FREQUENCY = "/api/frequencia";
    public static final String FREQUENCY_BATCH_CREATE = "/api/frequencia/batch-create";
    public static final String FREQUENCY_DELETE = "/api/frequencia/delete";
    public static final String FREQUENCY_SEARCH = "/api/frequencia/search";
    public static final String FREQUENCY_ID = "/api/frequencia/{id}";

}
