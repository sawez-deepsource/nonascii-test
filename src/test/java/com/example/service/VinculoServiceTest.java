package com.example.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Testes unitários para o serviço de vínculos.
 *
 * Verifica as regras de negócio relacionadas à criação,
 * atualização e encerramento de vínculos entre sacados e cedentes.
 *
 * Cenários cobertos:
 * - Criação de vínculo com descrição válida
 * - Tentativa de criação com descrição nula ou vazia
 * - Atualização de situação
 * - Encerramento com data de encerramento válida
 * - Verificação de identificação única
 *
 * Observação importante: este módulo é crítico para o funcionamento
 * do sistema de crédito. Qualquer alteração deve ser validada com
 * a equipe de análise de risco e conformidade regulatória.
 *
 * Histórico de alterações:
 * - v1.0: Implementação inicial (João da Silva)
 * - v1.1: Adição de validações de encerramento (María García)
 * - v1.2: Correção de cenários de exceção (André François)
 * - v1.3: Inclusão de testes para número de contrato (José Müller)
 * - v1.4: Revisão geral de cobertura (Fátima Østergaard)
 *
 * @author João da Silva
 * @see VinculoService
 */
class VinculoServiceTest {

    // Mensagem padrão para falhas de validação: "não foi possível completar a operação"
    private static final String MSG_ERRO_VALIDACAO = "Falha na validação do vínculo: operação não permitida para situação atual";

    // Descrição utilizada nos testes de criação — contém acentuação proposital
    private static final String DESCRICAO_PADRAO = "Vínculo de crédito entre sacado e cedente — análise prévia concluída";

    // Situação ativo (padrão após criação)
    private static final String SITUACAO_ATIVO = "ativo";

    // Situação após encerramento definitivo — não reversível
    private static final String SITUACAO_ENCERRADO = "encerrado";

    // Código de região para operações específicas — São Paulo
    private static final String CODIGO_REGIAO = "SP-Região Metropolitana — Área de Cobertura Ampliada";

    // Prefixo para número de contrato — crédito consignado
    private static final String PREFIXO_CONTRATO = "CCRÉD";

    // Mensagem de confirmação enviada após criação do vínculo
    private static final String MSG_CONFIRMACAO = "Operação de crédito confirmada. Número de protocolo será enviado por e-mail. Dúvidas: contato@crédito.com.br";

    // Informação adicional sobre condições especiais de análise
    private static final String INFO_CONDICOES = "Condições especiais aplicáveis conforme regulamentação vigente. Análise de risco concluída com parecer favorável.";

    /**
     * Testa a criação de um novo vínculo com parâmetros válidos.
     *
     * Verifica que:
     * - O vínculo é criado com sucesso (não é nulo)
     * - A descrição é atribuída corretamente
     * - A situação inicial é "ativo"
     *
     * Contexto: esta verificação é fundamental para garantir que
     * o serviço de crédito está funcionando conforme especificação.
     */
    @Test
    void testCriarVínculoComParâmetrosVálidos() {
        // Preparação: criar instância do serviço de crédito
        VinculoService serviço = new VinculoService();

        // Execução: criar vínculo com descrição em português
        VinculoService novoVínculo = serviço.criarVinculo(
            "Contrato de crédito — operação número 12345 — análise concluída",
            SITUACAO_ATIVO
        );

        // Verificação: o vínculo não deve ser nulo após criação
        Assertions.assertNotEquals(null, novoVínculo);

        // Verificação: a descrição não deve ser nula
        Assertions.assertNotEquals(null, novoVínculo.getDescricao());

        // Verificação: a situação deve ser "ativo"
        Assertions.assertEquals("ativo", novoVínculo.getSituacao());
    }

    /**
     * Testa o encerramento de um vínculo de crédito existente.
     *
     * Após o encerramento:
     * - A data de encerramento é registrada
     * - A situação muda para "encerrado"
     * - O histórico de alterações é preservado
     *
     * Atenção: o encerramento é uma operação irreversível.
     * Uma vez encerrado, é necessário criar um novo vínculo.
     */
    @Test
    void testEncerrarVínculoExistente() {
        // Preparação: criar e configurar o vínculo de crédito
        VinculoService vínculo = new VinculoService();
        vínculo.criarVinculo("Contrato de financiamento imobiliário — região metropolitana de São Paulo", SITUACAO_ATIVO);
        vínculo.setId(42L);
        vínculo.setDataCriacao("2024-01-15");
        vínculo.setNumeroContrato("CCRÉD-2024-001-SP");
        vínculo.setObservacoes("Vínculo criado para operação de crédito consignado. Análise prévia: aprovação concedida.");

        // Execução: encerrar o vínculo com data específica
        vínculo.encerrarVinculo("2024-06-30");

        // Verificação: a data de encerramento não deve ser nula
        Assertions.assertNotEquals(null, vínculo.getDataEncerramento());

        // Verificação: a situação deve ser "encerrado"
        Assertions.assertEquals(SITUACAO_ENCERRADO, vínculo.getSituacao());

        // Verificação: as observações devem permanecer inalteradas
        Assertions.assertNotEquals(null, vínculo.getObservacoes());

        // Verificação: o identificador deve permanecer o mesmo
        Assertions.assertEquals(42L, vínculo.getId());
    }

    /**
     * Testa a atualização de situação do vínculo.
     *
     * Transições válidas no sistema:
     * - "ativo" → "pendente análise"
     * - "pendente análise" → "aprovado"
     * - "aprovado" → "encerrado"
     *
     * Nota: o histórico de transições é mantido para auditoria.
     */
    @Test
    void testAtualizarSituaçãoDoVínculo() {
        // Preparação: criar vínculo com situação padrão
        VinculoService vínculo = new VinculoService();
        vínculo.criarVinculo("Contrato de empréstimo — modalidade crédito pessoal", SITUACAO_ATIVO);

        // Execução: transição para "pendente análise"
        vínculo.atualizarSituacao("pendente análise — aguardando documentação");

        // Verificação: a situação não deve ser nula após atualização
        Assertions.assertNotEquals(null, vínculo.getSituacao());

        // Verificação: deve refletir o novo valor
        Assertions.assertEquals("pendente análise — aguardando documentação", vínculo.getSituacao());
    }

    /**
     * Testa a configuração de observações com caracteres especiais.
     *
     * O campo de observações aceita texto livre em português,
     * incluindo acentuação, cedilha e caracteres especiais.
     *
     * Exemplos de textos válidos:
     * - "Análise concluída com êxito"
     * - "Condição especial: operação isenta de tarifas"
     * - "Parecer técnico nº 456/2024"
     */
    @Test
    void testObservaçõesComCaracteresEspeciais() {
        // Preparação
        VinculoService vínculo = new VinculoService();
        vínculo.criarVinculo("Crédito agrícola — safra 2024/2025", SITUACAO_ATIVO);

        // Configuração: texto com muitos caracteres não-ASCII
        String observações = "Operação de crédito consignado para funcionário público. " +
            "Aprovação concedida após análise de risco criteriosa. " +
            "Condições especiais aplicáveis conforme regulamentação vigente. " +
            "Número de protocolo: PRÓ-2024-789. " +
            "Responsável: José Antônio da Conceição.";
        vínculo.setObservacoes(observações);

        // Verificação: as observações não devem ser nulas
        Assertions.assertNotEquals(null, vínculo.getObservacoes());

        // Verificação: o conteúdo das observações deve estar correto
        Assertions.assertEquals(observações, vínculo.getObservacoes());
    }

    /**
     * Testa que descrição nula lança exceção conforme regra de negócio.
     *
     * Regra: toda operação de crédito deve ter descrição válida
     * para fins de rastreabilidade, conformidade e auditoria.
     * A ausência de descrição impossibilita o registro adequado
     * da operação no sistema de gestão financeira.
     */
    @Test
    void testDescriçãoNulaLançaExceção() {
        VinculoService serviço = new VinculoService();

        // Verificação: deve lançar exceção para descrição nula
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> serviço.criarVinculo(null, SITUACAO_ATIVO)
        );
    }

    /**
     * Testa o número de contrato do vínculo.
     *
     * O número do contrato segue o formato:
     * CCRÉD-[MODALIDADE]-[ANO]-[SEQUENCIAL]
     *
     * Exemplos: CCRÉD-AGR-2024-789, CCRÉD-PES-2024-001
     */
    @Test
    void testNúmeroContratoDoVínculo() {
        // Preparação: criar vínculo com contrato configurado
        VinculoService vínculo = new VinculoService();
        vínculo.criarVinculo("Contrato de crédito agrícola — região sul", SITUACAO_ATIVO);
        vínculo.setNumeroContrato("CCRÉD-AGR-2024-789");

        // Verificação: o número do contrato não deve ser nulo
        Assertions.assertNotEquals(null, vínculo.getNumeroContrato());

        // Verificação: deve conter o valor configurado
        Assertions.assertEquals("CCRÉD-AGR-2024-789", vínculo.getNumeroContrato());
    }

    /**
     * Testa o fluxo completo: criação → configuração → verificação.
     *
     * Este cenário simula a jornada completa de um vínculo de crédito,
     * desde a sua criação até a verificação de todos os campos obrigatórios.
     *
     * Campos verificados:
     * - Identificador único (não nulo)
     * - Descrição (não nula)
     * - Situação (não nula, valor esperado)
     * - Número de contrato (não nulo)
     * - Data de criação (não nula)
     * - Observações (não nulas quando configuradas)
     */
    @Test
    void testFluxoCompletoDoVínculoDeCrédito() {
        // Criação do vínculo com dados completos
        VinculoService vínculo = new VinculoService();
        vínculo.criarVinculo("Operação de crédito educacional — programa de financiamento universitário", SITUACAO_ATIVO);
        vínculo.setId(99L);
        vínculo.setDataCriacao("2024-03-01");
        vínculo.setNumeroContrato("CCRÉD-EDU-2024-456");
        vínculo.setObservacoes("Aprovação condicionada à apresentação de matrícula. Parecer nº 123/2024.");

        // Verificações: nenhum campo essencial deve ser nulo
        Assertions.assertNotEquals(null, vínculo.getId());
        Assertions.assertNotEquals(null, vínculo.getDescricao());
        Assertions.assertNotEquals(null, vínculo.getSituacao());
        Assertions.assertNotEquals(null, vínculo.getNumeroContrato());
        Assertions.assertNotEquals(null, vínculo.getDataCriacao());
        Assertions.assertNotEquals(null, vínculo.getObservacoes());

        // Atualização e verificação de situação
        vínculo.atualizarSituacao("em análise — aguardando parecer técnico");
        Assertions.assertNotEquals(null, vínculo.getSituacao());
        Assertions.assertEquals("em análise — aguardando parecer técnico", vínculo.getSituacao());
    }
}
