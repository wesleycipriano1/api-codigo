package api_code.entity;

import api_code.dto.ClasseRequestDTO;
import api_code.exception.LinguagemNaoSuportadaException;

public class ClasseFactory {

    public static String gerar(ClasseRequestDTO dto) {
        switch (dto.getLinguagem().toLowerCase()) {
            case "java":
                return gerarJava(dto);
            case "python":
                return gerarPython(dto);
            default:
                throw new LinguagemNaoSuportadaException("Linguagem não suportada");
        }
    }

    private static String gerarJava(ClasseRequestDTO dto) {
        StringBuilder sb = new StringBuilder();

        sb.append(dto.getEncapsulamentoClasse())
                .append(" class ")
                .append(dto.getNomeClasse());

        if (dto.getHeranca() != null && !dto.getHeranca().isEmpty()) {
            sb.append(" extends ").append(dto.getHeranca());
        }

        sb.append(" {\n");

        for (ClasseRequestDTO.AtributoDTO atributo : dto.getAtributos()) {
            sb.append("    ")
                    .append(atributo.getEncapsulamento())
                    .append(" ")
                    .append(atributo.getTipo())
                    .append(" ")
                    .append(atributo.getNome())
                    .append(";\n");
        }

        sb.append("}");

        return sb.toString();
    }

    private static String gerarPython(ClasseRequestDTO dto) {
        StringBuilder sb = new StringBuilder();

        sb.append("class ")
                .append(dto.getNomeClasse());

        if (dto.getHeranca() != null && !dto.getHeranca().isEmpty()) {
            sb.append("(").append(dto.getHeranca()).append(")");
        }

        sb.append(":\n");

        if (dto.getAtributos().isEmpty()) {
            sb.append("    pass\n");
        } else {
            sb.append("    def __init__(self");

            for (ClasseRequestDTO.AtributoDTO atributo : dto.getAtributos()) {
                sb.append(", ").append(atributo.getNome()).append(": ").append(atributo.getTipo());
            }

            sb.append("):\n");

            for (ClasseRequestDTO.AtributoDTO atributo : dto.getAtributos()) {
                sb.append("        self.")
                        .append(atributo.getNome())
                        .append(" = ")
                        .append(atributo.getNome())
                        .append("\n");
            }
        }

        return sb.toString();
    }
}
