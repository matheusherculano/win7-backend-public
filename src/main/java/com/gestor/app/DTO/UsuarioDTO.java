package com.gestor.app.DTO;

import java.time.LocalDateTime;
import java.util.Set;

import com.gestor.app.model.Ambiente;
import com.gestor.app.model.Role;

public record UsuarioDTO(Long id, String nome, String login, String senha, String email, String whatsapp, LocalDateTime timeStamp, Ambiente ambiente, Set<Role> roles) {

}
