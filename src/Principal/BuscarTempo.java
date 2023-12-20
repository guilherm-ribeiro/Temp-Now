package Principal;

import Modelos.InfoTempo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class BuscarTempo {
    public static void main(String[] args) {
        Scanner leitura = new Scanner(System.in);
        String busca = "";
        List<InfoTempo> listaTempo = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .registerTypeAdapter(InfoTempo.class, new InfoTempo.InfoTempoDeserializer())
                .setPrettyPrinting()
                .create();

        while (!busca.equalsIgnoreCase("sair")) {
            System.out.println("Digite o nome da cidade: ");
            busca = leitura.nextLine();

            if (busca.equalsIgnoreCase("sair")) {
                break;
            }

            String endereco = "https://api.hgbrasil.com/weather?key=07557ef4&city_name=" + busca.replaceAll(" ", "+");

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                //System.out.println(json); //para ver a impressão bruta do Json


                InfoTempo meuTempo = gson.fromJson(json, InfoTempo.class);
                meuTempo.setDate(meuTempo.getDateString());
                meuTempo.setTime(meuTempo.getTimeString());

                System.out.println("PREVISÃO DO TEMPO");
                System.out.println("Data da consulta: " + meuTempo.getDateString() + " hora: " + meuTempo.getTimeString());
                System.out.println("Cidade: " + meuTempo.getCity());
                System.out.println("Temperatura: " + (int) meuTempo.getTemp() + "ºC, " + meuTempo.getDescription());
                System.out.println("Temperatura Maxima: " + meuTempo.getMax() + "ºC, " + " Temperatura Minima: " + meuTempo.getMin() + "ºC,");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("O programa finalizou corretamente");
            }
        }
    }
}

