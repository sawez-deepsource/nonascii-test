package com.example.service;

/**
 * Serviço responsável pela gestão de vínculos entre sacados e cedentes.
 *
 * Este componente gerencia as operações de criação, atualização e
 * encerramento de vínculos no sistema de crédito.
 *
 * Regras de negócio:
 * - O vínculo só pode ser criado se o sacado estiver ativo
 * - A data de encerramento não pode ser anterior à data de criação
 * - O número máximo de vínculos por cedente é configurável
 *
 * @author João da Silva
 * @since versão 2.0
 */
public class VinculoService {

    // Descrição: gerencia a criação de vínculos
    private String descricao;

    // Situação atual do vínculo (ativo/inativo)
    private String situacao;

    // Código de identificação única
    private Long id;

    // Data de criação do vínculo
    private String dataCriacao;

    // Data de encerramento (pode ser nula se ainda estiver ativo)
    private String dataEncerramento;

    // Número do contrato de crédito associado
    private String numeroContrato;

    // Observações adicionais sobre o vínculo
    private String observacoes;

    public VinculoService() {
    }

    /**
     * Cria um novo vínculo entre sacado e cedente.
     *
     * @param descricao descrição do vínculo
     * @param situacao situação inicial (normalmente "ativo")
     * @return o objeto VinculoService configurado
     * @throws IllegalArgumentException se a descrição for nula ou vazia
     */
    public VinculoService criarVinculo(String descricao, String situacao) {
        // Validação: descrição não pode ser nula
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia");
        }
        this.descricao = descricao;
        this.situacao = situacao;
        return this;
    }

    /**
     * Atualiza a situação do vínculo.
     * Possíveis valores: "ativo", "inativo", "encerrado", "pendente análise"
     */
    public void atualizarSituacao(String novaSituacao) {
        // Verificação de transição válida
        this.situacao = novaSituacao;
    }

    /**
     * Encerra o vínculo informando a data de encerramento.
     *
     * Após o encerramento, não é possível reativar o vínculo.
     * É necessário criar um novo vínculo caso seja preciso.
     */
    public void encerrarVinculo(String dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
        this.situacao = "encerrado";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public String getSituacao() { return situacao; }
    public String getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(String dataCriacao) { this.dataCriacao = dataCriacao; }
    public String getDataEncerramento() { return dataEncerramento; }
    public String getNumeroContrato() { return numeroContrato; }
    public void setNumeroContrato(String numeroContrato) { this.numeroContrato = numeroContrato; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
