package com.api.ProjetoEstoque.service;

import com.api.ProjetoEstoque.data.UsuarioEntity;
import com.api.ProjetoEstoque.data.UsuarioRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean autenticar(String usuario, String senha) {
        Optional<UsuarioEntity> userOpt = usuarioRepository.findByUsuario(usuario);
        if (userOpt.isPresent()) {
            String senhaCriptografada = md5(senha);
            return senhaCriptografada.equals(userOpt.get().getSenha());
        }
        return false;
    }

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash MD5", e);
        }
    }
}
