package models;

public class CreateOrderDTO {
    private String[] ingredients;

    public CreateOrderDTO(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public CreateOrderDTO() {
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}