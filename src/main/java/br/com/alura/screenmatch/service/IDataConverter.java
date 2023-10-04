package br.com.alura.screenmatch.service;

public interface IDataConverter {
    <T> T getDatum(String json, Class<T> tClass);
}
