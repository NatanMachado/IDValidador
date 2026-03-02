package com.codeuai.idvalidator.service;

import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import com.codeuai.idvalidator.model.ValidationError;

@Service
public class CNPJService {

    // --- Constantes de domínio ---
    private final int CNPJ_BASE_LENGTH = 12;
    private final int CNPJ_FULL_LENGTH = 14;

    private final int DV_MODULO = 11;
    private final int DV_RESTO_ZERO = 0;
    private final int DV_RESTO_UM = 1;

    private final Pattern VALID_CHAR = Pattern.compile("^[0-9A-z]$");

    // Pesos definidos pela especificação (2 a 9, repetindo)
    private final int[] PESOS = { 2, 3, 4, 5, 6, 7, 8, 9 };

    // --- Conversão de caractere para valor numérico ---
    private int charToValue(char c) throws ValidationError {
        int ascii = c;
        return (c <= '9') ? ascii - '0' : ascii - 48;
    }

    // --- Cálculo de um dígito verificador ---
    private int calcularDV(String base) throws ValidationError {
        int soma = 0;
        int len = base.length();

        for (int i = 0; i < len; i++) {
            char c = base.charAt(len - 1 - i);
            int valor = charToValue(c);
            int peso = PESOS[i % PESOS.length];
            soma += valor * peso;
        }

        int resto = soma % DV_MODULO;

        if (resto == DV_RESTO_ZERO || resto == DV_RESTO_UM) {
            return 0;
        }

        return DV_MODULO - resto;
    }

    // --- Validação do CNPJ completo ---
    public boolean validar(String cnpj) throws ValidationError {
        ValidatorService.assertNotNull(cnpj, "O CNPJ NÃO PODE SER NULO.");
        ValidatorService.assertNotEmpty(cnpj, "O CNPJ NÃO PODE SER VAZIO.");

        var _cnpj = cnpj.replaceAll("[/.-]", "").toUpperCase();

        validarConteudo(_cnpj);
        ValidatorService.assertTrue(_cnpj.length() == CNPJ_FULL_LENGTH, cnpj,
                "NÚMERO DE DÍGITOS INCORRETO. DEVE SER %d.".formatted(CNPJ_FULL_LENGTH));

        validarDV(_cnpj);
        return true;
    }

    private void validarConteudo(String cnpj) throws ValidationError {
        for (char c : cnpj.toCharArray()) {
            ValidatorService.assertTrue(VALID_CHAR.matcher(String.valueOf(c)).matches(), cnpj, "CARACTERE INVÁLIDO: " + c);
        }
    }

    private void validarDV(String cnpj) throws ValidationError {
        String base = cnpj.substring(0, CNPJ_BASE_LENGTH);
        int dv1Informado = charToValue(cnpj.charAt(CNPJ_BASE_LENGTH));
        int dv2Informado = charToValue(cnpj.charAt(CNPJ_BASE_LENGTH + 1));

        int dv1Calculado = calcularDV(base);
        ValidatorService.assertTrue(dv1Calculado == dv1Informado, cnpj, "DÍGITO VERIFICADOR 1 INCORRETO.");

        int dv2Calculado = calcularDV(base + dv1Calculado);
        ValidatorService.assertTrue(dv2Calculado == dv2Informado, cnpj, "DÍGITO VERIFICADOR 2 INCORRETO.");
    }
}
