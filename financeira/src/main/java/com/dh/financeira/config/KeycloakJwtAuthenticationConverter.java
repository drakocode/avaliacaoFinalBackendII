package com.dh.financeira.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken>, org.springframework.core.convert.converter.Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        try{
            Collection<GrantedAuthority> authorities = getAuthorities(source);
            return new JwtAuthenticationToken(source, authorities);
        }catch(Exception e){
            throw new RuntimeException("not_authorized");
        }

    }

    @Override
    public <U> org.springframework.core.convert.converter.Converter<Jwt, U> andThen(org.springframework.core.convert.converter.Converter<? super AbstractAuthenticationToken, ? extends U> after) {
        return org.springframework.core.convert.converter.Converter.super.andThen(after);
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }

    private Collection<GrantedAuthority> getAuthorities(Jwt source) throws JsonProcessingException {
        Collection<GrantedAuthority> roles = new HashSet<>();
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

        final var tokenAsString=mapper.writeValueAsString(source);
        final var claims = mapper.readTree(tokenAsString).get("claims");





        roles.addAll(extractRoles("resource_acces", claims));
        roles.addAll(extractRoles("realm_acces", claims));
//        roles.addAll("scope", () ->);


        return null;
    }


    private static List<GrantedAuthority> extractRoles(String route, JsonNode jwt){
        final var rolesWithPrefix = new HashSet<>();

                jwt
                .path(route)
                .elements()
                .forEachRemaining(e -> e.path("roles")
                        .elements()
                        .forEachRemaining(role -> rolesWithPrefix.add("ROLE" + role.asText())));

            return AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));
    }




}
