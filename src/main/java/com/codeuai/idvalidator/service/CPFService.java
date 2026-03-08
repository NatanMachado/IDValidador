package com.codeuai.idvalidator.service;

import org.springframework.stereotype.Service;
import com.codeuai.idvalidator.model.ValidationError;

@Service
public class CPFService implements DocumentValidator {
    private static final int DV_MODULO = 11;
    private static final int DV_RESTO_CONST = 2;
    private static final int DV_ZERO = 0;
    private static final String MASK_CHARACTERS = "[.-]";

    @Override
    public boolean isValid(String document) throws ValidationError {
        ValidatorService.assertNotNull(document, "O CPF NÃO PODE SER NULO.");
        
        String cpf = document.replaceAll(MASK_CHARACTERS, ""); // Remove caracteres não numéricos
        
        ValidatorService.assertTrue(cpf.matches("(?!(\\d)\\1{10})\\d{11}"), document,
                "CPF DEVE CONTER 11 DÍGITOS E NÃO PODE TER TODOS OS DÍGITOS IGUAIS.");

        int digitoVerificador1Esperado = obterDigitoVerificador1Cpf(cpf);
        int digitoVerificador1Informado = Integer.parseInt(cpf.substring(9, 10));
        ValidatorService.assertTrue(digitoVerificador1Esperado == digitoVerificador1Informado, document,
                "DÍGITO VERIFICADOR 1 INCORRETO.");

        int digitoVerificador2Esperado = obterDigitoVerificador2Cpf(cpf);
        int digitoVerificador2Informado = Integer.parseInt(cpf.substring(10, 11));
        ValidatorService.assertTrue(digitoVerificador2Esperado == digitoVerificador2Informado, document,
                "DÍGITO VERIFICADOR 2 INCORRETO.");

        return true;
    }

    private static int obterDigitoVerificador1Cpf(String cpf) {
        final int[] multiplicadores = { 10, 9, 8, 7, 6, 5, 4, 3, 2 };
        final String novePrimeirosDigitos = cpf.substring(0, 9);
        return obterDigitoVerificador(novePrimeirosDigitos, multiplicadores);
    }

    private static int obterDigitoVerificador2Cpf(String cpf) {
        final int[] multiplicadores = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
        final String dezPrimeirosDigitos = cpf.substring(0, 10);
        return obterDigitoVerificador(dezPrimeirosDigitos, multiplicadores);
    }

    private static int obterDigitoVerificador(String digitos, int[] multiplicadores) {
        int soma = obterSomaDosMultiplicadores(digitos, multiplicadores);
        int restoDaDivisao = soma % DV_MODULO;
        if (restoDaDivisao < DV_RESTO_CONST) {
            return DV_ZERO;
        }
        return DV_MODULO - restoDaDivisao;
    }

    private static int obterSomaDosMultiplicadores(String digitos, int[] multiplicadores) {
        int indice = 0;
        int soma = 0;
        for (int mp : multiplicadores) {
            soma = soma + (mp * Integer.parseInt(digitos.substring(indice, indice + 1)));
            indice++;
        }
        return soma;
    }

}
