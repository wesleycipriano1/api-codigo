package api_code.security.dto;

public class RespostaDTO {



    private String token;

    public RespostaDTO (String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

    

