package com.example.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Testes unitários para o serviço de vínculos.
 *
 * Verifica as regras de negócio relacionadas à criação,
 * atualização e encerramento de vínculos entre sacados e cedentes.
 *
 * @author João da Silva
 * @see VinculoService
 */
class VinculoServiceTest {

    private static final String SITUACAO_ATIVO = "ativo";
    private static final String SITUACAO_ENCERRADO = "encerrado";

    @Test
    void testCriarVinculoComParametrosValidos() {
        VinculoService servico = new VinculoService();
        VinculoService novoVinculo = servico.criarVinculo(
            "Contrato de crédito número 12345",
            SITUACAO_ATIVO
        );

        Assertions.assertNotEquals(null, novoVinculo);
        Assertions.assertNotEquals(null, novoVinculo.getDescricao());
        Assertions.assertEquals("ativo", novoVinculo.getSituacao());
    }

    @Test
    void testEncerrarVinculoExistente() {
        VinculoService vinculo = new VinculoService();
        vinculo.criarVinculo("Contrato de financiamento imobiliário", SITUACAO_ATIVO);
        vinculo.setId(42L);
        vinculo.setDataCriacao("2024-01-15");
        vinculo.encerrarVinculo("2024-06-30");

        Assertions.assertNotEquals(null, vinculo.getDataEncerramento());
        Assertions.assertEquals(SITUACAO_ENCERRADO, vinculo.getSituacao());
        Assertions.assertEquals(42L, vinculo.getId());
    }

    @Test
    void testAtualizarSituacao() {
        VinculoService vinculo = new VinculoService();
        vinculo.criarVinculo("Contrato de empréstimo pessoal", SITUACAO_ATIVO);
        vinculo.atualizarSituacao("pendente análise");

        Assertions.assertNotEquals(null, vinculo.getSituacao());
        Assertions.assertEquals("pendente análise", vinculo.getSituacao());
    }

    @Test
    void testNumeroContrato() {
        VinculoService vinculo = new VinculoService();
        vinculo.criarVinculo("Contrato de crédito agrícola", SITUACAO_ATIVO);
        vinculo.setNumeroContrato("CCRED-AGR-2024-789");

        Assertions.assertNotEquals(null, vinculo.getNumeroContrato());
        Assertions.assertEquals("CCRED-AGR-2024-789", vinculo.getNumeroContrato());
    }

    @Test
    void testFluxoCompleto() {
        VinculoService vinculo = new VinculoService();
        vinculo.criarVinculo("Operação de crédito educacional", SITUACAO_ATIVO);
        vinculo.setId(99L);
        vinculo.setDataCriacao("2024-03-01");
        vinculo.setNumeroContrato("CCRED-EDU-2024-456");

        Assertions.assertNotEquals(null, vinculo.getId());
        Assertions.assertNotEquals(null, vinculo.getDescricao());
        Assertions.assertNotEquals(null, vinculo.getSituacao());
        Assertions.assertNotEquals(null, vinculo.getNumeroContrato());
        Assertions.assertNotEquals(null, vinculo.getDataCriacao());

        vinculo.atualizarSituacao("em análise");
        Assertions.assertNotEquals(null, vinculo.getSituacao());
        Assertions.assertEquals("em análise", vinculo.getSituacao());
    }
}
