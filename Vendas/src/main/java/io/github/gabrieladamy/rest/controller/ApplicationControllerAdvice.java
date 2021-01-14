package io.github.gabrieladamy.rest.controller;

import io.github.gabrieladamy.exception.PedidoNaoEncontradoException;
import io.github.gabrieladamy.exception.RegraNegocioException;
import io.github.gabrieladamy.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class) // marca o metodo para tratar erros
    @ResponseStatus(HttpStatus.BAD_REQUEST) //retorna erro 400
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex){

        String messageErro = ex.getMessage();
        return new ApiErrors(messageErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex){
        return  new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().
                getAllErrors().
                stream().
                map(erro -> erro.getDefaultMessage()).
                collect(Collectors.toList());
        return new ApiErrors(errors);
    }
}
