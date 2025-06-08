package api_code.factory;



import api_code.dto.ClasseRequestDTO;
import api_code.exception.LinguagemNaoSuportadaException;
import api_code.generetor.ClassGenerator;
import api_code.generetor.JavaClassGenerator;
import api_code.generetor.PythonClassGenerator;


public class ClassGeneratorFactory {

    public static ClassGenerator getGenerator(ClasseRequestDTO dto) {
        switch (dto.getLinguagem().toLowerCase()) {
            case "java":
                return new JavaClassGenerator();
            case "python":
                return new PythonClassGenerator();
            default:
                throw new LinguagemNaoSuportadaException("Linguagem não suportada: " + dto.getLinguagem());
        }
    }
}
