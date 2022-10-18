	.file	"Programa.cpp"
	.text
	.def	___main;	.scl	2;	.type	32;	.endef
	.section .rdata,"dr"
	.align 4
LC0:
	.ascii "Ingrese un numero por favor perra : \0"
LC1:
	.ascii "%d\0"
	.align 4
LC2:
	.ascii "Ingrese otro numero por favor: \0"
LC3:
	.ascii "\12SENTENCIA IF-ELSE : \0"
	.align 4
LC4:
	.ascii "Numero 1 (%d) es mayor que Numero 2 (%d) \12\12\0"
	.align 4
LC5:
	.ascii "Numero 2 (%d) es mayor que Numero 1 (%d) \12\12\0"
LC6:
	.ascii "pause\0"
LC7:
	.ascii "cls\0"
	.align 4
LC8:
	.ascii "\12Ingrese otro numero para probar el \"FOR\" por favor: \0"
LC9:
	.ascii "\12SENTENCIA FOR : \0"
LC10:
	.ascii "\12  Iteracion %d, numero: %d \0"
	.text
	.globl	_main
	.def	_main;	.scl	2;	.type	32;	.endef
_main:
LFB32:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	andl	$-16, %esp
	subl	$32, %esp
	call	___main
	movl	$10, 20(%esp)
	movl	$5, 16(%esp)
	movl	$LC0, (%esp)
	call	_printf
	leal	20(%esp), %eax
	movl	%eax, 4(%esp)
	movl	$LC1, (%esp)
	call	_scanf
	movl	$LC2, (%esp)
	call	_printf
	leal	16(%esp), %eax
	movl	%eax, 4(%esp)
	movl	$LC1, (%esp)
	call	_scanf
	movl	$LC3, (%esp)
	call	_puts
	movl	20(%esp), %edx
	movl	16(%esp), %eax
	cmpl	%eax, %edx
	jle	L2
	movl	16(%esp), %edx
	movl	20(%esp), %eax
	movl	%edx, 8(%esp)
	movl	%eax, 4(%esp)
	movl	$LC4, (%esp)
	call	_printf
	jmp	L3
L2:
	movl	20(%esp), %edx
	movl	16(%esp), %eax
	movl	%edx, 8(%esp)
	movl	%eax, 4(%esp)
	movl	$LC5, (%esp)
	call	_printf
L3:
	movl	$LC6, (%esp)
	call	_system
	movl	$LC7, (%esp)
	call	_system
	movl	$LC8, (%esp)
	call	_printf
	leal	20(%esp), %eax
	movl	%eax, 4(%esp)
	movl	$LC1, (%esp)
	call	_scanf
	movl	$1, 28(%esp)
	movl	$LC9, (%esp)
	call	_puts
	movl	20(%esp), %eax
	movl	%eax, 24(%esp)
L5:
	movl	20(%esp), %eax
	addl	$9, %eax
	cmpl	%eax, 24(%esp)
	jg	L4
	movl	24(%esp), %eax
	movl	%eax, 8(%esp)
	movl	28(%esp), %eax
	movl	%eax, 4(%esp)
	movl	$LC10, (%esp)
	call	_printf
	addl	$1, 28(%esp)
	addl	$1, 24(%esp)
	jmp	L5
L4:
	movl	$10, (%esp)
	call	_putchar
	movl	$LC6, (%esp)
	call	_system
	movl	$0, %eax
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE32:
	.ident	"GCC: (MinGW.org GCC Build-2) 9.2.0"
	.def	_printf;	.scl	2;	.type	32;	.endef
	.def	_scanf;	.scl	2;	.type	32;	.endef
	.def	_puts;	.scl	2;	.type	32;	.endef
	.def	_system;	.scl	2;	.type	32;	.endef
	.def	_putchar;	.scl	2;	.type	32;	.endef
