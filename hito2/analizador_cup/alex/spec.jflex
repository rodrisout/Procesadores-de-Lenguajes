package alex;
import errors.GestionErroresTiny;

%%
%line
%column
%class AnalizadorLexicoTiny
%type  UnidadLexica
%unicode
%public
%cup

%{
  private ALexOperations ops;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = [\+,\-]?({digitoPositivo}{digito}*|0)
parteDecimal = ({digito}*{digitoPositivo}|0)
parteExponencial = (e|E){parteEntera}
bool = (b|B)(o|O)(o|O)(l|L)
int = (i|I)(n|N)(t|T)
real = (r|R)(e|E)(a|A)(l|L)
string = (s|S)(t|T)(r|R)(i|I)(n|N)(g|G)
and = (a|A)(n|N)(d|D)
or = (o|O)(r|R)
not = (n|N)(o|O)(t|T)
true = (t|T)(r|R)(u|U)(e|E)
false = (f|F)(a|A)(l|L)(s|S)(e|E)
null = (n|N)(u|U)(l|L)(l|L)
proc = (p|P)(r|R)(o|O)(c|C)
if = (i|I)(f|F)
else = (e|E)(l|L)(s|S)(e|E)
while = (w|W)(h|H)(i|I)(l|L)(e|E)
struct = (s|S)(t|T)(r|R)(u|U)(c|C)(t|T)
new = (n|N)(e|E)(w|W)
delete = (d|D)(e|E)(l|L)(e|E)(t|T)(e|E)
read = (r|R)(e|E)(a|A)(d|D)
write = (w|W)(r|R)(i|I)(t|T)(e|E)
nl = (n|N)(l|L)
type = (t|T)(y|Y )(p|P)(e|E)
call = (c|C)(a|A)(l|L)(l|L)
literalEntero = {parteEntera}
literalReal = {parteEntera}(\.{parteDecimal}|{parteExponencial}|\.{parteDecimal}{parteExponencial})
literalCadena = \"[^\"]*\"
identificador = (\_|{letra})({letra}|{digito}|\_)*
suma = \+
resta = \-
mul = \*
div = \/
mod = \%
menor = \<
mayor = \>
igual = \=\=
distinto = \!\=
menorIgual = \<\=
mayorIgual = \>\=
asig = \=
finalAsig = \&\&
parenApert = \(
parenCierre = \)
llaveApert = \{
llaveCierre = \}
puntoComa = \;
coma = \,
punto = \.
arroba = \@
indireccion = \^
paramRef = \&
corcheteApert = \[
corcheteCierre = \]
separador = [ \t\r\b\n]
comentario = ##[^\n]* 

%%
{bool}					{return ops.unidadBool();}
{int}                   {return ops.unidadInt();}
{real}                  {return ops.unidadReal();}
{string}                {return ops.unidadString();}
{and}                   {return ops.unidadAnd();}
{or}                    {return ops.unidadOr();}
{not}                   {return ops.unidadNot();}
{true}                  {return ops.unidadTrue();}
{false}                 {return ops.unidadFalse();}
{null}                  {return ops.unidadNull();}
{proc}                  {return ops.unidadProc();}
{if}                    {return ops.unidadIf();}
{else}                  {return ops.unidadElse();}
{while}                 {return ops.unidadWhile();}
{struct}                {return ops.unidadStruct();}
{new}                   {return ops.unidadNew();}
{delete}                {return ops.unidadDelete();}
{read}                  {return ops.unidadRead();}
{write}                 {return ops.unidadWrite();}
{nl}                    {return ops.unidadNL();}
{type}                  {return ops.unidadType();}
{call}                  {return ops.unidadCall();}
{literalEntero}			{return ops.unidadLitEnt();}
{literalReal}			{return ops.unidadLitReal();}
{literalCadena}         {return ops.unidadLitCadena();}
{identificador}         {return ops.unidadIden();}
{suma}                  {return ops.unidadSuma();}
{resta}                 {return ops.unidadResta();}
{mul}                   {return ops.unidadMul();}
{div}                   {return ops.unidadDiv();}
{mod}                   {return ops.unidadMod();}
{menor}                 {return ops.unidadMenor();}
{mayor}                 {return ops.unidadMayor();}
{igual}                 {return ops.unidadIgual();}
{distinto}				{return ops.unidadDistinto();}
{menorIgual}            {return ops.unidadMenorIgual();}
{mayorIgual}            {return ops.unidadMayorIgual();}
{asig}                  {return ops.unidadAsig();}
{finalAsig}             {return ops.unidadFinalAsig();}
{parenApert}            {return ops.unidadParenApert();}
{parenCierre}           {return ops.unidadParenCierre();}
{llaveApert}            {return ops.unidadLlaveApert();}
{llaveCierre}           {return ops.unidadLlaveCierre();}
{puntoComa}             {return ops.unidadPuntoComa();}
{coma}                  {return ops.unidadComa();}
{punto}                 {return ops.unidadPunto();}
{arroba}                {return ops.unidadArroba();}
{indireccion}           {return ops.unidadIndireccion();}
{paramRef}              {return ops.unidadParamRef();}
{corcheteApert}         {return ops.unidadCorcheteApert();}
{corcheteCierre}        {return ops.unidadCorcheteCierre();}
{separador}             {}
{comentario}            {}
[^]                     {ops.error();}  