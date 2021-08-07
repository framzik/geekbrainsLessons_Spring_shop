package ru.khrebtov.persist;

public class CategoryListParam {

    private String categoryNameFilter;

    private String categoryDescriptionFilter;

    private Integer page;

    private Integer size;

    private String sortField;

    public String getCategoryNameFilter() {
        return categoryNameFilter;
    }

    public void setCategoryNameFilter(String categoryNameFilter) {
        this.categoryNameFilter = categoryNameFilter;
    }

    public String getCategoryDescriptionFilter() {
        return categoryDescriptionFilter;
    }

    public void setCategoryDescriptionFilter(String categoryDescriptionFilter) {
        this.categoryDescriptionFilter = categoryDescriptionFilter;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
}
