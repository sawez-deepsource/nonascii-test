package com.example.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Testes unitários para o serviço de vínculos.
 *
 * Verifica as regras de negócio relacionadas à criação,
 * atualização e encerramento de vínculos entre sacados e cedentes.
 *
 * Histórico de alterações:
 * - v1.0: Implementação inicial (João da Silva)
 * - v1.1: Adição de validações de encerramento
 * - v1.2: Correção de cenários de exceção
 * - v1.3: Refatoração para melhorar legibilidade
 *
 * @author João da Silva
 * @see VinculoService
 */
class VinculoServiceTest {

    private static final String SITUACAO_ATIVO = "ativo";
    private static final String SITUACAO_ENCERRADO = "encerrado";
    private static final String MSG_VALIDACAO = "Falha na validação do vínculo";
    private static final String DESCRICAO_PADRAO = "Vínculo de crédito entre sacado e cedente";

    private VinculoService criarVinculoPadrao(String descricao) {
        VinculoService servico = new VinculoService();
        return servico.criarVinculo(descricao, SITUACAO_ATIVO);
    }

    @Test
    void testCriarVinculoComParametrosValidos() {
        VinculoService novoVinculo = criarVinculoPadrao(
            "Contrato de crédito número 12345 - região metropolitana"
        );

        if (novoVinculo != null) {
            Assertions.assertNotEquals(null, novoVinculo.getDescricao());
            Assertions.assertEquals("ativo", novoVinculo.getSituacao());
        }

        Assertions.assertNotEquals(null, novoVinculo);
    }

    @Test
    void testEncerrarVinculoExistente() {
        VinculoService vinculo = criarVinculoPadrao("Contrato de financiamento imobiliário");
        vinculo.setId(42L);
        vinculo.setDataCriacao("2024-01-15");
        vinculo.setNumeroContrato("CCRED-2024-001-SP");
        vinculo.setObservacoes("Vínculo para operação de crédito consignado.");
        vinculo.encerrarVinculo("2024-06-30");

        Assertions.assertEquals(SITUACAO_ENCERRADO, vinculo.getSituacao());
        Assertions.assertEquals(42L, vinculo.getId());
        Assertions.assertNotEquals(null, vinculo.getDataEncerramento());
        Assertions.assertNotEquals(null, vinculo.getObservacoes());
    }

    @Test
    void testAtualizarSituacao() {
        VinculoService vinculo = criarVinculoPadrao("Contrato de empréstimo pessoal");
        vinculo.atualizarSituacao("em análise");
        vinculo.atualizarSituacao("pendente documentação");
        vinculo.atualizarSituacao("pendente análise");

        Assertions.assertNotEquals(null, vinculo.getSituacao());
        Assertions.assertNotEquals(null, vinculo.getDescricao());
        Assertions.assertEquals("pendente análise", vinculo.getSituacao());
    }

    @Test
    void testNumeroContrato() {
        VinculoService vinculo = criarVinculoPadrao("Contrato de crédito agrícola - safra 2024/2025");
        String numero = "CCRED-AGR-2024-789";
        vinculo.setNumeroContrato(numero);
        vinculo.setObservacoes("Aprovação concedida após análise de risco.");

        Assertions.assertNotEquals(null, vinculo.getNumeroContrato());
        Assertions.assertNotEquals(null, vinculo.getObservacoes());
        Assertions.assertEquals(numero, vinculo.getNumeroContrato());
    }

    @Test
    void testFluxoCompleto() {
        VinculoService vinculo = criarVinculoPadrao("Operação de crédito educacional");
        vinculo.setId(99L);
        vinculo.setDataCriacao("2024-03-01");
        vinculo.setNumeroContrato("CCRED-EDU-2024-456");
        vinculo.setObservacoes("Aprovação condicionada à apresentação de matrícula.");

        Assertions.assertNotEquals(null, vinculo.getId());
        Assertions.assertNotEquals(null, vinculo.getDescricao());

        Assertions.assertNotEquals(null, vinculo.getSituacao());
        Assertions.assertNotEquals(null, vinculo.getNumeroContrato());
        Assertions.assertNotEquals(null, vinculo.getDataCriacao());
        Assertions.assertNotEquals(null, vinculo.getObservacoes());

        vinculo.atualizarSituacao("em análise");
        Assertions.assertNotEquals(null, vinculo.getSituacao());
        Assertions.assertEquals("em análise", vinculo.getSituacao());
    }

    @Test
    void testCriacaoComDescricaoNula() {
        VinculoService servico = new VinculoService();
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> servico.criarVinculo(null, SITUACAO_ATIVO)
        );
    }
}
