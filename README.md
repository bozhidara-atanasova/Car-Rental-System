# Car Rental System – конзолно приложение на Java

## 1. Какво представлява
* Малка програма за изпита по ООП.  
* Позволява да добавяш, редактираш, наемаш и връщаш автомобили.  
* Данните се пазят в **CSV файл** (`data/cars.csv`) – без външни библиотеки.

##2. Command in the Console
add    <id> <make> <model> <year> <type>
edit   <id> поле=стойност [поле=стойност ...]
remove <id>
rent   <id> <ИмеКлиент> <yyyy-MM-dd>
return <id>
list                    # свободни коли
search поле=стойност    # напр. search status=RENTED
save                    # запазва CSV
help
exit

## 3.Как изглеждат данните
Id,Make,Model,Year,Type,Status,CurrentRenter

## 4.Структура на кода
src/
 ├─ Vehicle.java       (абстрактен базов клас)
 ├─ Car.java           (автомобил – наследява Vehicle)
 ├─ Customer.java
 ├─ Rentable.java      (интерфейс)
 ├─ Searchable.java    (интерфейс)
 ├─ CarFileReader.java / CarFileWriter.java
 ├─ CarRentalService.java  
 ├─ RentalManager.java     (CLI)
 └─ CarRentalApp.java      (main)

