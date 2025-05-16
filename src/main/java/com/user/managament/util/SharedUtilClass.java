package com.user.managament.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.function.Function;

public class SharedUtilClass {
    public static boolean setPropToEdit(String propToVerify) {
        return propToVerify != null && !propToVerify.isBlank() && !propToVerify.isEmpty();
    }

    public static  <E, D> List<D> retornaListaFormatada(List<E> entidade, Function<E, D> conversor) {
        return entidade.stream()
                .map(conversor)
                .toList();
    }

    public static String captura(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
