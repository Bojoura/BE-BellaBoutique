//package com.bella.BellaBoutique.mappers;
//
//import com.bella.BellaBoutique.DTO.ProductDTO;
//import com.bella.BellaBoutique.DTO.ReviewDTO;
//import com.bella.BellaBoutique.model.products.Product;
//import com.bella.BellaBoutique.model.reviews.Review;
//
//public class ProductMapper {
//
//    public static ProductDTO toProductDTO(Product product) {
//        ProductDTO dto = new ProductDTO();
//
//        dto.setId(product.getId());
//        dto.setTitle(product.getTitle());
//        dto.setDescription(product.getDescription());
//        dto.setCategory(product.getCategory());
//        dto.setPrice(product.getPrice());
//        dto.setDiscountPercentage(product.getDiscountPercentage());
//        dto.setRating(product.getRating());
//        dto.setStock(product.getStock());
//        dto.setBrand(product.getBrand());
//        dto.setSku(product.getSku());
//        dto.setWeight(product.getWeight());
//        dto.setWidth(product.getWidth());
//        dto.setHeight(product.getHeight());
//        dto.setDepth(product.getDepth());
//        dto.setWarrantyInformation(product.getWarrantyInformation());
//        dto.setShippingInformation(product.getShippingInformation());
//        dto.setAvailabilityStatus(product.getAvailabilityStatus());
//        dto.setReturnPolicy(product.getReturnPolicy());
//        dto.setBarcode(product.getBarcode());
//        dto.setQrCode(product.getQrCode());
//        dto.setThumbnail(product.getThumbnail());
//        dto.setTags(product.getTags());
//        dto.setImages(product.getImages());
//
////        dto.setReviews(product.getReviews().stream().map(ProductMapper::toReviewDTO).toList());
//
//        return dto;
//    }
//
//    private static ReviewDTO toReviewDTO(Review review) {
//        ReviewDTO dto = new ReviewDTO();
//        dto.setId(review.getId());
//        dto.setReviewerName(review.getReviewerName());
//        dto.setComment(review.getComment());
//        dto.setRating(review.getRating());
//        dto.setReviewDate(review.getReviewDate());
//        return dto;
//    }
//}
//
