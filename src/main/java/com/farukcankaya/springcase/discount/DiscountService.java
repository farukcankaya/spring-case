package com.farukcankaya.springcase.discount;

import com.farukcankaya.springcase.campaign.CampaignRepository;
import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.campaign.entity.DiscountType;
import com.farukcankaya.springcase.discount.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {
  private CampaignRepository campaignRepository;

  @Autowired
  public DiscountService(CampaignRepository campaignRepository) {
    this.campaignRepository = campaignRepository;
  }

  public List<Product> calculateDiscounts(List<Product> items) {
    // Initial: make discounted price is normal price
    items.forEach(product -> product.setDiscountedPrice(product.getPrice()));
    // Make discount for product campaign
    items
        .stream()
        .forEach(product -> applyDiscountToProduct(product, calculateProductDiscount(product)));

    // Make discount for category campaign
    items
        .stream()
        .map(product -> product.getCategoryId())
        .distinct()
        .forEach(
            categoryId -> {
              Product maxPricedProduct =
                  items
                      .stream()
                      .filter(
                          product -> product.getCategoryId().longValue() == categoryId.longValue())
                      .max(Comparator.comparing(Product::getPrice))
                      .get();
              applyDiscountToProduct(maxPricedProduct, calculateCategoryDiscount(maxPricedProduct));
            });
    return items;
  }

  private BigDecimal calculateProductDiscount(Product product) {
    Optional<Campaign> optionalCampaign =
        campaignRepository.findByProductId(product.getProductId());

    return calculateDiscount(optionalCampaign, product);
  }

  private BigDecimal calculateCategoryDiscount(Product product) {
    Optional<Campaign> optionalCampaign =
        campaignRepository.findByCategoryId(product.getCategoryId());

    return calculateDiscount(optionalCampaign, product);
  }

  private BigDecimal calculateDiscount(Optional<Campaign> optionalCampaign, Product product) {
    if (!optionalCampaign.isPresent()) return BigDecimal.ZERO;
    Campaign campaign = optionalCampaign.get();

    BigDecimal discount;
    if (campaign.getDiscountType().equals(DiscountType.RATE)) {
      discount =
          product.getPrice().multiply(campaign.getDiscountValue()).divide(new BigDecimal(100));

      if (discount.compareTo(campaign.getMaximumDiscountPrice()) > 0) {
        discount = campaign.getMaximumDiscountPrice();
      }
    } else {
      discount = campaign.getDiscountValue();
    }
    return discount;
  }

  private void applyDiscountToProduct(Product product, BigDecimal discount) {
    BigDecimal totalDiscount = product.getDiscountedPrice().subtract(discount);
    if (totalDiscount.compareTo(BigDecimal.ZERO) < 0) {
      product.setDiscountedPrice(BigDecimal.ZERO);
    } else {
      product.setDiscountedPrice(totalDiscount);
    }
  }
}
