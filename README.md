# Car Insurance Calculator 🚗💵

This individual project involves the development of a car insurance premium calculator, a tool designed to help customers determine the cost of their car insurance policy. The calculator takes into account various factors, including the sum insured (market price of the car), engine size (cc), coverage type (First Party or Third Party), No Claim Discount (NCD), and the option to include additional windscreen cover.

## Premium Calculation Formula

The formula to calculate the insurance premium is as follows:

1. **Calculate Basic Premium:**
   - For West Malaysia:
     - Comprehensive rate for the first RM1,000 sum insured (from Table 1) + RM26 for each RM1,000 or part thereof on value exceeding the first RM1,000.
   - For East Malaysia:
     - Comprehensive rate for the first RM1,000 sum insured (from Table 1) + RM20.30 for each RM1,000 or part thereof on value exceeding the first RM1,000.

2. **Apply No Claim Discount (NCD):**
   - Discounts based on the NCD percentage (0%, 25%, 30%, 38.33%, or 45%) will be applied to the basic premium.

3. **Additional Windscreen Cover (Optional):**
   - If the customer chooses to add additional windscreen cover, RM120 will be added to the total cost.

## Premium Calculation Example

Consider the following example:
- Basic Premium Market Value = RM 20,000
- Engine size = 1500cc
- Coverage = First Party
- Basic Premium = 305.50 + (20,000-1000)/1000 * 26 = RM799.50
- NCD 25%
- Premium after NCD = RM599.63
- Windscreen cover: If yes, add RM120 to the premium after NCD

## Features

- Dummy online payment.
- Generate/print PDF of the insurance details.

## Private Car Schedule of Premiums (Table 1)

The sum insured will be the market value of the car.

![Car Insurance Calculator](Screenshot 2024-01-11 022745.png)


**Note:** Please refer to the detailed Private Car Schedule of Premiums (Table 1) for comprehensive rates.

Feel free to contribute, report issues, or suggest improvements to enhance the fun
