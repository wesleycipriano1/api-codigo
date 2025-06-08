package api_code.generetor;

import api_code.dto.ClasseRequestDTO;

public class PythonClassGenerator implements ClassGenerator {

    @Override
    public String generate(ClasseRequestDTO dto) {
        StringBuilder sb = new StringBuilder();
        
        
        sb.append("class ")
          .append(dto.getNomeClasse());
        if (dto.getHeranca() != null && !dto.getHeranca().isEmpty()) {
            sb.append("(").append(dto.getHeranca()).append(")");
        }
        sb.append(":\n");
        
        
        if (dto.getAtributos().isEmpty()) {
           
            sb.append("    def __init__(self):\n");
            sb.append("        pass\n\n");
        } else {
           
            sb.append("    def __init__(self");
            for (ClasseRequestDTO.AtributoDTO atributo : dto.getAtributos()) {
                sb.append(", ").append(atributo.getNome())
                  .append(": ").append(atributo.getTipo());
            }
            sb.append("):\n");
            for (ClasseRequestDTO.AtributoDTO atributo : dto.getAtributos()) {
                sb.append("        self.").append(atributo.getNome())
                  .append(" = ").append(atributo.getNome()).append("\n");
            }
            sb.append("\n");
        }
        
        
        for (ClasseRequestDTO.AtributoDTO atributo : dto.getAtributos()) {
            
            sb.append("    def get_").append(atributo.getNome())
              .append("(self) -> ").append(atributo.getTipo()).append(":\n");
            sb.append("        return self.").append(atributo.getNome()).append("\n\n");
            
            
            sb.append("    def set_").append(atributo.getNome())
              .append("(self, value: ").append(atributo.getTipo()).append(") -> None:\n");
            sb.append("        self.").append(atributo.getNome()).append(" = value\n\n");
        }
        
        
        sb.append("    def __str__(self) -> str:\n");
        sb.append("        return f\"").append(dto.getNomeClasse()).append("(");
        
        boolean first = true;
        for (ClasseRequestDTO.AtributoDTO atributo : dto.getAtributos()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(atributo.getNome()).append("={self.").append(atributo.getNome()).append("}");
            first = false;
        }
        sb.append(")\"\n\n");
        
        
        sb.append("    def __hash__(self) -> int:\n");
        sb.append("        return hash((");
        first = true;
        for (ClasseRequestDTO.AtributoDTO atributo : dto.getAtributos()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append("self.").append(atributo.getNome());
            first = false;
        }
        sb.append("))\n");
        
        return sb.toString();
    }
}
