package com.epam.lab.library.util.pagination;

/**
 * Paging class
 */
public class Paging {

    private final int DEFAULT_PAGE_NUMBER = 1;
    private final int DEFAULT_COUNT_PER_PAGE = 10;
    private int pageNumber;
    private int countPerPage;

    /**
     * Initializes a newly created Paging object
     */
    public Paging() {
        this.pageNumber = DEFAULT_PAGE_NUMBER;
        this.countPerPage = DEFAULT_COUNT_PER_PAGE;
    }

    /**
     * Initializes a newly created Paging object with certain page number with count per page
     *
     * @param pageNumber   Number of page
     * @param countPerPage Amount of objects per page
     */
    public Paging(int pageNumber, int countPerPage) {
        this.pageNumber = pageNumber;
        this.countPerPage = countPerPage;
    }

    /**
     * Returns page number
     *
     * @return Page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets page number
     *
     * @param pageNumber Page number
     * @return Paging object
     */
    public Paging setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    /**
     * Returns count of objects per page
     *
     * @return Count of objects per page
     */
    public int getCountPerPage() {
        return countPerPage;
    }

    public Paging setCountPerPage(int countPerPage) {
        this.countPerPage = countPerPage;
        return this;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Pagination{" +
                ", pageNumber=" + pageNumber +
                ", countPerPage=" + countPerPage +
                '}';
    }

    /**
     * Indicates whether some other object is "equal to" this one
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paging)) return false;

        Paging paging = (Paging) o;

        if (pageNumber != paging.pageNumber) return false;
        return countPerPage == paging.countPerPage;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result = pageNumber;
        result = 31 * result + countPerPage;
        return result;
    }
}