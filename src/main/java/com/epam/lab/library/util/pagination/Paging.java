package com.epam.lab.library.util.pagination;

public class Paging {

    private final int DEFAULT_PAGE_NUMBER = 1;
    private final int DEFAULT_COUNT_PER_PAGE = 10;
    private int pageNumber;
    private int countPerPage;

    public Paging() {
        this.pageNumber = DEFAULT_PAGE_NUMBER;
        this.countPerPage = DEFAULT_COUNT_PER_PAGE;
    }

    public Paging(int pageNumber, int countPerPage) {
        this.pageNumber = pageNumber;
        this.countPerPage = countPerPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public Paging setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public int getCountPerPage() {
        return countPerPage;
    }

    public Paging setCountPerPage(int countPerPage) {
        this.countPerPage = countPerPage;
        return this;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                ", pageNumber=" + pageNumber +
                ", countPerPage=" + countPerPage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paging)) return false;

        Paging paging = (Paging) o;

        if (pageNumber != paging.pageNumber) return false;
        return countPerPage == paging.countPerPage;
    }

    @Override
    public int hashCode() {
        int result = pageNumber;
        result = 31 * result + countPerPage;
        return result;
    }
}