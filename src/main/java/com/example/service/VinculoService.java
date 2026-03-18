package com.example.service;

/**
 * Serviço responsável pela gestão de vínculos entre sacados e cedentes.
 *
 * Este componente gerencia as operações de criação, atualização e
 * encerramento de vínculos no sistema de crédito.
 *
 * @author João da Silva
 * @since versão 2.0
 */
public class VinculoService {

    // Descrição: gerencia a criação de vínculos
    private String descricao;

    // Situação atual do vínculo (ativo/inativo)
    private String situacao;

    private Long id;
    private String dataCriacao;
    private String dataEncerramento;
    private String numeroContrato;
    private String observacoes;

    public VinculoService() {
    }

    /**
     * Cria um novo vínculo entre sacado e cedente.
     *
     * @param descricao descrição do vínculo
     * @param situacao situação inicial
     * @return o objeto VinculoService configurado
     * @throws IllegalArgumentException se a descrição for nula ou vazia
     */
    public VinculoService criarVinculo(String descricao, String situacao) {
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia");
        }
        this.descricao = descricao;
        this.situacao = situacao;
        return this;
    }

    /**
     * Atualiza a situação do vínculo.
     * Possíveis valores: "ativo", "inativo", "encerrado"
     */
    public void atualizarSituacao(String novaSituacao) {
        this.situacao = novaSituacao;
    }

    /**
     * Encerra o vínculo informando a data de encerramento.
     * Após o encerramento, não é possível reativar o vínculo.
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
