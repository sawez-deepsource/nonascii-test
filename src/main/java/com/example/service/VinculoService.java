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

    private String descricao;
    private String situacao;
    private Long id;
    private String dataCriacao;
    private String dataEncerramento;
    private String numeroContrato;
    private String observacoes;

    public VinculoService() {
    }

    public VinculoService criarVinculo(String descricao, String situacao) {
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia");
        }
        this.descricao = descricao;
        this.situacao = situacao;
        return this;
    }

    public void atualizarSituacao(String novaSituacao) {
        this.situacao = novaSituacao;
    }

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
