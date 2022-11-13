#include <stdio.h>
#include <stdlib.h>
int suma(int arg1, int arg2)
{
    int add;
    asm("addl %%ebx, %%eax;"
        : "=a"(add)
        : "a"(arg1), "b"(arg2));
    return add;
}
int resta(int arg1, int arg2)
{
    int sub;
    asm("subl %%ebx, %%eax;"
        : "=a"(sub)
        : "a"(arg1), "b"(arg2));
    return sub;
}
int multiplicacion(int arg1, int arg2)
{
    int mul;
    asm("imull %%ebx, %%eax;"
        : "=a"(mul)
        : "a"(arg1), "b"(arg2));
    return mul;
}
int division(int arg1, int arg2)
{
    int quo, rem;
    asm("movl $0x0, %%edx;"
        "movl %2, %%eax;"
        "movl %3, %%ebx;"
        "idivl %%ebx;"
        : "=a"(quo), "=d"(rem)
        : "g"(arg1), "g"(arg2));
    return rem, quo;
}

int potencia(int arg1, int arg2)
{
    printf("(%i) ^ (%i) = ", arg1, arg2);
    int res;
    __asm__ __volatile__(
        "movl %1, %%eax;"
        "movl %2, %%ecx;"
        "dec %%ecx;"
        "siguiente:;"
        "movl %1, %%edx;"
        "mul %%edx;"
        "loop siguiente;"
        "movl %%eax,%0;"
        : "=g"(res)
        : "g"(arg1), "g"(arg2));
    return res;
}
void asm_sqrt(double num)
{
    double or = num;
    __asm__ __volatile__(
        "fsqrt"
        : "+t"(num));
    printf("Raiz de %.2lf = %.2lf\n", or, num);
}
void asm_cos(double num)
{
    double or = num;
    __asm__ __volatile__(
        "fcos"
        : "+t"(num));
    printf("COS %.2lf rad = %.4lf\n", or, num);
}
void asm_sen(double num)
{
    double res = num;
    __asm__ __volatile__(
        "fsin"
        : "+t"(num));
    printf("SEN %.2lf rad= %.4lf\n", res, num);
}
void asm_tan(double num)
{
    double or = num;
    __asm__ __volatile__(
        "fptan"
        : "+t"(num));
    printf("TAN %.2lf rad= %.4lf\n", or, num);
}
int main()
{
    int arg1, arg2, add, sub, mul, div, pot;
    float arg3, raizc, randians;
    int opcion, opcion1;
    do
    {
        // opcion operacion basicas, opcion conversion mas operaciones
        printf(" 1.Operaciones basicas \n");
        printf(" 2.Conversiones y mas operaciones \n");
        printf(" 3.Salir \n");
        printf(" Elija una opcion: ");
        scanf("%d", &opcion);
        switch (opcion)
        {
        case 1:
            // Operaciones basicas
            printf(" Ingrese el primer numero perra: ");
            scanf("%d", &arg1);
            printf("\n Ingrese el segundo numero: ");
            scanf("%d", &arg2);
            // menu de operaciones basicas
            printf(" 1.Suma \n");
            printf(" 2.Resta \n");
            printf(" 3.Multiplicacion \n");
            printf(" 4.Division \n");
            printf(" 5.Potencia \n");
            printf(" Elija una opcion: \n");
            scanf("%d", &opcion);
            if (opcion == 1)
            {
                add = suma(arg1, arg2);
                printf(" La suma es: %d \n", add);
                break;
            }
            else if (opcion == 2)
            {
                sub = resta(arg1, arg2);
                printf(" La resta es: %d \n", sub);
                break;
            }
            else if (opcion == 3)
            {
                mul = multiplicacion(arg1, arg2);
                printf(" La multiplicacion es: %d \n", mul);
                break;
            }
            else if (opcion == 4)
            {
                div = division(arg1, arg2);
                printf(" La division es: %d \n", div);
                break;
            }
            else if (opcion == 5)
            {
                pot = potencia(arg1, arg2);
                printf(" La potencia es: %d \n", pot);
                break;
            }
            else
            {
                printf(" Opcion no valida \n");
                break;
            }
        case 2:
            printf(" Ingrese el primer numero: ");
            // scanf("%f", &arg3);
            // if(opcion1==1){
            //     if(arg3<0){
            //         printf(" No se puede calcular la raiz cuadrada de un numero negativo \n");
            //         break;
            //     }
            //     else{
            //         raizc = square_root(arg3);
            //         printf(" La raiz cuadrada es: %f \n", raizc);
            //         break;
            //     }
            // }
            // if(opcion1==2){
            //     randians = cosx(arg3);
            //     printf(" El coseno es: %f \n", randians);
            //     break;
            // }
            // else{
            //     printf(" Opcion no valida \n");
            //     break;
            // }
        case 3:
            printf(" Adios\n");
            break;
        default:
            printf(" Opcion invalida\n");
        }
        system("PAUSE");
        system("CLS");
    } while (opcion != 3);
}
