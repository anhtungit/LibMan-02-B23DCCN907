package libman.entity;

public class Document7 {
    private int documentId;
    private String title;
    private String author;
    private String publisher;
    private Integer publishYear;
    private String docType;
    private int totalQuantity;
    private String location;
    private String description;

    public Document7() {}

    public Document7(int documentId, String title, String author, String publisher, Integer publishYear,
                     String docType, int totalQuantity, String location, String description) {
        this.documentId = documentId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.docType = docType;
        this.totalQuantity = totalQuantity;
        this.location = location;
        this.description = description;
    }

    public void increaseQuantity(int qty) {
        if (qty > 0) this.totalQuantity += qty;
    }

    public void decreaseQuantity(int qty) {
        if (qty > 0 && this.totalQuantity >= qty) {
            this.totalQuantity -= qty;
        }
    }

    public boolean checkAvailable(int qty) {
        return this.totalQuantity >= qty;
    }

    public int getDocumentId() { return documentId; }
    public void setDocumentId(int documentId) { this.documentId = documentId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getPublishYear() { return publishYear; }
    public void setPublishYear(Integer publishYear) { this.publishYear = publishYear; }

    public String getDocType() { return docType; }
    public void setDocType(String docType) { this.docType = docType; }

    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

