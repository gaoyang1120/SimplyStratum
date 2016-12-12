# SimplyStratum
SimplyStratum is a light version of Stratum project. No UI, no servers, no handlers or other stuff. Only manually-rannable package with console control.

## How to run
1) Prepare IntelliJIDEA project, then push "run"
2) Use console help: `help`
3) Load demo-data: `load ./data/`

## Common problems
- Do not leave empty lines in data' files. It will load, by can cause unhandled exceptions. This files should be clean! Currently I can't add checking for simple lines when memory allocating.

## CSV files? What the?
Visit [Finam](https://www.finam.ru/profile/forex/eur-usd/export/) to download required data or export from Metatrader. Required structure:
| Date 	| Time 	| Open 	| High 	| Low 	| Close 	| Other Stuff   |
|------	|------	|------	|------	|-----	|-------	| -----------   |
Place this data files to `/data/` folder.
Also, you can download old data, not only 2009-2016. It will be automatically used. Current set is optimal for high speed and good results.

## Console logging configuration
Go to `/src/main/resources/log4j.properties` and edit required lines. More info  is avalible on [Log4j guide](https://logging.apache.org/log4j/2.x/).

## What is the model?
Stratum is based on handlers, they make calculations on the air. That is really simple - create Factory instance and push it Handlers.class.