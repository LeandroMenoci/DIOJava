package br.com.dio;

import br.com.dio.dto.UserDTO;
import br.com.dio.mapper.UserMapper;
import br.com.dio.model.UserModel;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

public class Main {

    private static final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    public static void main(String[] args) {

        var model = new UserModel();
        model.setUserName("Maria");
        model.setCode(1);
        model.setBrithday(LocalDate.now().minusYears(30));
        System.out.println(mapper.toDto(model));

        var dto = new UserDTO();
        dto.setName("Rubens");
        dto.setId(2);
        dto.setBrithday(LocalDate.now().minusYears(40));
        System.out.println(mapper.toModel(dto));

    }
}