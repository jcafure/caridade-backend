ALTER TABLE menu_campaign_donation_items
DROP CONSTRAINT if exists fk_menu_campaign_donation_item;

ALTER TABLE menu_campaign_donation_items
ADD CONSTRAINT fk_menu_campaign_donation_item
FOREIGN KEY (donation_items_id)
REFERENCES donation_item(id)
ON DELETE CASCADE;