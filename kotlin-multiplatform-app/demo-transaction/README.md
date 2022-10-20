# demo-transactions

Simple application that will fetch a list of transactions from an api endpoint and present the data to user.

## Build variants

- `dataApresentationA<Debug/Release>`
- `dataBpresentationA<Debug/Release>`
- `dataApresentationB<Debug/Release>`
- `dataBpresentationB<Debug/Release>`

Project contains two *flavorDimensions* and four *productFlavors*, and they can be used to build different versions of app based on which `data` and `presentation` layer app going to take.

#### `dataA`

- Take module `transaction_data_1`
- Persistent the latest data stored in database
- Consuming API: https://60df9ae9abbdd9001722d437.mockapi.io/api/v1/transactions1
```json
// Example of model signature
[
    {
        "id":"1",
        "transactionDate":"2021-02-02T08:11:16+13:00",
        "summary":"Fancy Food Market Auckland",
        "debit":197.9,
        "credit":0
    },
    {
        "id":"2",
        "transactionDate":"2021-02-03T00:06:37+13:00",
        "summary":"Parking Auckland",
        "debit":8.25,
        "credit":0
    }
]
```

#### `dataB`

- Take module `transaction_data_2`
- The latest data cache in-memory
- Consuming API: https://60df9ae9abbdd9001722d437.mockapi.io/api/v1/transactions2
```json
// Example of model signature
[
    {
        "id":"1",
        "date":"2021-02-02T08:11:16+13:00",
        "description":"Trans with positive money",
        "money":150.99
    },
    {
        "id":"2",
        "date":"2021-02-03T00:06:37+13:00",
        "description":"Trans with negative money",
        "money":-101.99
    }
]
```

#### `presentationA`
- Take module `transaction_presentation_1`
- First page
  - Display transactions in a sorted list
  - Select a transaction will take user to a second page

| <img src="https://github.com/jiangyang5157/kotlin-multiplatform-mobile/tree/master/kotlin-multiplatform-app/demo-transaction/assets/report-dataA-presentationA.png" width="320"> |
|:--:|
| *assets/report-dataA-presentationA* |

| <img src="https://github.com/jiangyang5157/kotlin-multiplatform-mobile/tree/master/kotlin-multiplatform-app/demo-transaction/assets/report-dataB-presentationA.png" width="320"> |
|:--:|
| *assets/report-dataB-presentationA* |

- Second page
  - Display detailed view of the transaction data
    - 15% included gst calculation for debit transaction

| <img src="https://github.com/jiangyang5157/kotlin-multiplatform-mobile/tree/master/kotlin-multiplatform-app/demo-transaction/assets/transaction-debit-presentationA.png" width="320"> |
|:--:|
| *assets/transaction-debit-presentationA* |

| <img src="https://github.com/jiangyang5157/kotlin-multiplatform-mobile/tree/master/kotlin-multiplatform-app/demo-transaction/assets/transaction-credit-presentationA.png" width="320"> |
|:--:|
| *assets/transaction-credit-presentationA* |

#### `presentationB`
- Take module `transaction_presentation_2`
- Frist page
  - Display transactions in a sorted list
    - 15% included gst calculation for debit transaction
    - Divide line for each item

| <img src="https://github.com/jiangyang5157/kotlin-multiplatform-mobile/tree/master/kotlin-multiplatform-app/demo-transaction/assets/report-dataA-presentationB.png" width="320"> |
|:--:|
| *assets/report-dataA-presentationB* |

| <img src="https://github.com/jiangyang5157/kotlin-multiplatform-mobile/tree/master/kotlin-multiplatform-app/demo-transaction/assets/report-dataB-presentationB.png" width="320"> |
|:--:|
| *assets/report-dataB-presentationB* |

## Tech Stack
- Clean Architecture
- Layer Modularization
- MVVM
- Dagger Hilt
- KotlinX Coroutines
- Squareup Retrofit2
- AndroidX Room
- AndroidX Navigation
