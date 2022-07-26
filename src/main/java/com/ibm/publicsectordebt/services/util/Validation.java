package com.ibm.publicsectordebt.services.util;

import com.ibm.publicsectordebt.entities.BcData;
import net.minidev.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Validation {

    /**
     * Verifica o campo passado
     * @param field dia, mes ou ano
     * @param value valor da data
     * @return boolean
     */
    public static boolean validadFieldValueDate(String field, Integer value) {
        String[] fields = {"day", "month", "year"};
        boolean result = false;

        for (String s : fields) {
            if (field.contains(s)) {
                result = true;
                break;
            }
        }

        if (result) {
            switch (field) {
                case "day":
                    result = (value >= 1 && value <= 31);
                    break;

                case "month":
                    result = (value >= 1 && value <= 12);
                    break;

                case "year":
                    result = (value >= 1900 && value <= 3000);
                    break;
            }
        }
        return result;
    }

    /**
     * Calcular a soma por ano e passar para JSONObject
     * @param listValue
     * @param year
     * @return JSONObject
     */
    public static JSONObject debtValid(List<Double> listValue, Integer year) {
        JSONObject obj = new JSONObject();
        double sum = listValue.stream().mapToDouble(x -> x).sum();

        obj.put("ano", year);
        obj.put("periodo", listValue.size() + " mes(s)");
        obj.put("soma", String.format("%.3f", sum));
        obj.put("mensagem", "Dívida líquida do setor público");

        return obj;
    }

    /**
     * Resumo dos dados
     * @param list
     * @return JSONObject
     */
    public static JSONObject summaryValid(List<BcData> list) {
        var obj = new JSONObject();

        double media = (double) list.stream().mapToDouble(x -> x.getValor()).average().orElse(0);
        int size = (int) list.stream().distinct().count();
        BcData minValue = Collections.min(list, Comparator.comparing(BcData::getValor));
        BcData maxValue = Collections.max(list, Comparator.comparing(BcData::getValor));
        BcData minDate = Collections.min(list, Comparator.comparing(BcData::getData));
        BcData maxDate = Collections.max(list, Comparator.comparing(BcData::getData));

        obj.put("media_valores", new DecimalFormat("#,###0.000").format(media));
        obj.put("quantity_data", size);
        obj.put("valor_minimo", minValue);
        obj.put("valor_maximo", maxValue);
        obj.put("data_minima", minDate);
        obj.put("data_maxima", maxDate);

        return obj;
    }

    /**
     * Verificar se a lista possui valores nulos
     * @param list
     * @return boolean
     */
    public static Boolean verifyNull(List<BcData> list) {
        boolean result = false;
        for (BcData l: list) {
            if (l.getValor() == null || l.getData() == null) {
                result = true;
                break;
            }
        }
        return result;
    }
}
