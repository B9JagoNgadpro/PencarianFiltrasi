package jagongadpro.pencarianfiltrasi.dto;

public class FilterGameRequest {

    private String name;
    private String category;
    private Integer minPrice;
    private Integer maxPrice;

    public FilterGameRequest() {
    }

    public FilterGameRequest(String name, String category, Integer minPrice, Integer maxPrice) {
        this.name = name;
        this.category = category;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }
}