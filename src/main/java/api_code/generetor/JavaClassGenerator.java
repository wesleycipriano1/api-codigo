package api_code.generetor;

import api_code.dto.ClasseRequestDTO;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaClassGenerator implements ClassGenerator {

    
    private static final List<String> PRIMITIVE_TYPES = Arrays.asList(
        "byte", "short", "int", "long", "float", "double", "boolean", "char"
    );

    @Override
    public String generate(ClasseRequestDTO dto) {
        validateInput(dto);
        
        StringBuilder sb = new StringBuilder();
        
        
        
        
        addRequiredImports(sb, dto);
        
        
        generateClassDeclaration(sb, dto);
        
       
        generateAttributes(sb, dto);
        
        
        generateConstructors(sb, dto);
        
       
        generateAccessors(sb, dto);
        
        
        generateSpecialMethods(sb, dto);
        
        sb.append("}");
        
        return sb.toString();
    }

    private void validateInput(ClasseRequestDTO dto) {
        if (dto.getNomeClasse() == null || dto.getNomeClasse().isEmpty()) {
            throw new IllegalArgumentException("Nome da classe é obrigatório");
        }
        
        if (dto.getEncapsulamentoClasse() == null) {
            dto.setEncapsulamentoClasse("public");
        }
    }

    private void addRequiredImports(StringBuilder sb, ClasseRequestDTO dto) {
        boolean needObjectsImport = dto.getAtributos() != null && 
                                   !dto.getAtributos().isEmpty() && 
                                   dto.getAtributos().stream()
                                      .anyMatch(a -> !isPrimitive(a.getTipo()));
        
        if (needObjectsImport) {
            sb.append("import java.util.Objects;\n\n");
        }
    }

    private void generateClassDeclaration(StringBuilder sb, ClasseRequestDTO dto) {
        sb.append(dto.getEncapsulamentoClasse())
          .append(" class ")
          .append(dto.getNomeClasse());
        
        
        if (dto.getHeranca() != null && !dto.getHeranca().isEmpty()) {
            sb.append(" extends ").append(dto.getHeranca());
        }
        
       
        if (dto.getInterfaces() != null && !dto.getInterfaces().isEmpty()) {
            sb.append(" implements ")
              .append(String.join(", ", dto.getInterfaces()));
        }
        
        sb.append(" {\n\n");
    }

    private void generateAttributes(StringBuilder sb, ClasseRequestDTO dto) {
        if (dto.getAtributos() != null) {
            for (ClasseRequestDTO.AtributoDTO atributo : dto.getAtributos()) {

                
                sb.append("    ")
                  .append(atributo.getEncapsulamento() != null ? atributo.getEncapsulamento() : "private")
                  .append(" ")
                  .append(atributo.getTipo())
                  .append(" ")
                  .append(atributo.getNome())
                  .append(";\n");
            }
            sb.append("\n");
        }
    }

    private void generateConstructors(StringBuilder sb, ClasseRequestDTO dto) {
        
        sb.append("    public ").append(dto.getNomeClasse()).append("() {}\n\n");
        
        
        if (dto.getAtributos() != null && !dto.getAtributos().isEmpty()) {
            sb.append("    public ").append(dto.getNomeClasse()).append("(");
            
            String params = dto.getAtributos().stream()
                .map(a -> a.getTipo() + " " + a.getNome())
                .collect(Collectors.joining(", "));
            
            sb.append(params).append(") {\n");
            
            for (ClasseRequestDTO.AtributoDTO atributo : dto.getAtributos()) {
                sb.append("        this.").append(atributo.getNome())
                  .append(" = ").append(atributo.getNome()).append(";\n");
            }
            
            sb.append("    }\n\n");
        }
    }

    private void generateAccessors(StringBuilder sb, ClasseRequestDTO dto) {
        if (dto.getAtributos() != null) {
            for (ClasseRequestDTO.AtributoDTO atributo : dto.getAtributos()) {
                
                sb.append("    public ").append(atributo.getTipo())
                  .append(" get").append(capitalize(atributo.getNome())).append("() {\n")
                  .append("        return ").append(atributo.getNome()).append(";\n")
                  .append("    }\n\n");
                
               
                sb.append("    public void set").append(capitalize(atributo.getNome()))
                  .append("(").append(atributo.getTipo()).append(" ").append(atributo.getNome()).append(") {\n")
                  .append("        this.").append(atributo.getNome())
                  .append(" = ").append(atributo.getNome()).append(";\n")
                  .append("    }\n\n");
            }
        }
    }

    private void generateSpecialMethods(StringBuilder sb, ClasseRequestDTO dto) {
        generateToString(sb, dto);
        generateHashCode(sb, dto);
        generateEquals(sb, dto);
    }

    private void generateToString(StringBuilder sb, ClasseRequestDTO dto) {
        sb.append("    @Override\n")
          .append("    public String toString() {\n")
          .append("        return \"")
          .append(dto.getNomeClasse()).append("{\" +\n");
        
        if (dto.getAtributos() != null && !dto.getAtributos().isEmpty()) {
            String attributes = dto.getAtributos().stream()
                .map(a -> "\"" + a.getNome() + "=\" + " + a.getNome())
                .collect(Collectors.joining(" + \", \" +\n                "));
            
            sb.append("                ").append(attributes).append(" +\n");
        }
        
        sb.append("                '}';\n")
          .append("    }\n\n");
    }

    private void generateHashCode(StringBuilder sb, ClasseRequestDTO dto) {
        sb.append("    @Override\n")
          .append("    public int hashCode() {\n");
        
        if (dto.getAtributos() == null || dto.getAtributos().isEmpty()) {
            sb.append("        return 0;\n");
        } else {
            sb.append("        return Objects.hash(");
            String hashFields = dto.getAtributos().stream()
                .map(ClasseRequestDTO.AtributoDTO::getNome)
                .collect(Collectors.joining(", "));
            sb.append(hashFields).append(");\n");
        }
        
        sb.append("    }\n\n");
    }

    private void generateEquals(StringBuilder sb, ClasseRequestDTO dto) {
        sb.append("    @Override\n")
          .append("    public boolean equals(Object o) {\n")
          .append("        if (this == o) return true;\n")
          .append("        if (o == null || getClass() != o.getClass()) return false;\n")
          .append("        ").append(dto.getNomeClasse()).append(" that = (")
          .append(dto.getNomeClasse()).append(") o;\n");
        
        if (dto.getAtributos() == null || dto.getAtributos().isEmpty()) {
            sb.append("        return true;\n");
        } else {
            sb.append("        return ");
            String comparisons = dto.getAtributos().stream()
                .map(a -> {
                    if (isPrimitive(a.getTipo())) {
                        return a.getNome() + " == that." + a.getNome();
                    } else {
                        return "Objects.equals(" + a.getNome() + ", that." + a.getNome() + ")";
                    }
                })
                .collect(Collectors.joining(" &&\n               "));
            
            sb.append(comparisons).append(";\n");
        }
        
        sb.append("    }\n\n");
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private boolean isPrimitive(String type) {
        return type != null && PRIMITIVE_TYPES.contains(type);
    }
}