package server.ingestion;

import java.util.List;

public interface DataNormalizer {

    List<NormalizedData> normalize(RawData rawData);
}