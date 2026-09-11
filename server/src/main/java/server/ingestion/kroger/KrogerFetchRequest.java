package server.ingestion;

public class KrogerFetchRequest {

    private String locationId;
    private String searchTerm;
    private Integer limit;
    private Integer offset;

    public KrogerFetchRequest(
            String locationId,
            String searchTerm,
            Integer limit,
            Integer offset
    ) {
        this.locationId = locationId;
        this.searchTerm = searchTerm;
        this.limit = limit;
        this.offset = offset;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }
}