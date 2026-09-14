package server.ingestion.kroger;

public class KrogerQuery {

    private String locationId;
    private String searchTerm;
    private int offset;
    private int limit;

    public KrogerQuery(
            String locationId,
            String searchTerm,
            int offset,
            int limit
    ) {
        this.locationId = locationId;
        this.searchTerm = searchTerm;
        this.offset = offset;
        this.limit = limit;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}