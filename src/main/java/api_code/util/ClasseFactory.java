package api_code.util;
import api_code.dto.ClasseRequestDTO;
import api_code.factory.ClassGeneratorFactory;
import api_code.generetor.ClassGenerator;


public class ClasseFactory {

    public static String gerar(ClasseRequestDTO dto) {
        ClassGenerator generator = ClassGeneratorFactory.getGenerator(dto);
        return generator.generate(dto);
    }
}