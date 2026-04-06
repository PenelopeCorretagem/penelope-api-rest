package penelope.corretagem.penelopeapirest.infrastructure.api.dto.booking;

public record Pagination(
        Integer totalItems,
        Integer remainingItems,
        Integer returnedItems,
        Integer itemsPerPage,
        Integer currentPage,
        Integer totalPages,
        Boolean hasNextPage,
        Boolean hasPreviousPage
) {
}
