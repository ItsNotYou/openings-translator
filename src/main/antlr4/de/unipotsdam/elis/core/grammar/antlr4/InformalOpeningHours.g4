grammar InformalOpeningHours;

openings : line ('\n' line)* ;

line : DAY('-'DAY)? ':' TIME'-'TIME 'Uhr'? ;

TIME : [0-9][0-9](':'|'.')[0-9][0-9] ;

DAY :
	'Mo'('ntag' | 'nday')? |
	'Di''enstag'? | 'Tu''esday'? |
	'Mi''ttwoch'? | 'We''dnesday'? |
	'Do''nnerstag'? | 'Th''ursday'? |
	'Fr'('eitag' | 'iday')? |
	'Sa'('mstag' | 'turday')? |
	'So''nntag'? | 'Su'('nday'?) ;

WS : ' ' -> skip ;
