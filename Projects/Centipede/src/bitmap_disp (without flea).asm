.data
	displayAddress:	.word 0x10008000
	#player
	bugLocation: .word 1007  #|change the original one to make sure bug bluster at the bottom of the screen；即从992 到 1023|
	dartLocation: .word 975  # | 子弹的位置在 player 的正上面 |
	#centiped
	centipedLocation: .word 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10   # |10 is the head, body is red, head is green|
	centipedLife: .word 3   		# | 当life为0的时候，centiped死掉 |
	
	oldlocation: 	  .word 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
	leftborder: .word -32#0
	rightborder: .word 31
	bottomcond: .word 0  # | 1的时候-1，0的时候+1 , 这里不仅仅只用于bottom line |
	#mushrooms
	mushroomsLocation: .word 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19  # |我们有20个蘑菇|
	mushroomsExist: .word 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1     # | 1表示蘑菇存在，0表示蘑菇不存在 |
	mushroomsLife: .word 3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3 	# | 当life为0的时候，mushrooms 死掉 |
	#================================================================================centipedDirection: .word 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1   #增加了一位，因为增加了一个头部
.text 

Main:
	jal random_mushrooms # |把随机的20mushrooms的location存入“mushroomsLocation”|
	jal draw_mushrooms   # |画出每一个蘑菇|
Loop:	
	jal delay
	
	la $t1, centipedLife
	lw $t2, 0($t1)
	bne $t2,$zero,run_centipede
	j donot_run_centipede
	run_centipede:
		jal disp_centiped
	donot_run_centipede:
		jal check_keystroke

		j Loop	

Exit:
	li $v0, 10		# terminate the program gracefully
	syscall


# function to display a static centiped	
disp_centiped:
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)    # |here, $ra is the address of the Loop. so the code means we tore the address of Loop into the stack| 
	
	addi $t5, $zero, 10	 # |$a3是loop count|=============更改了a3到t5,
	la $a1, centipedLocation # load the address of the array into $a1
	la $t9, oldlocation
	
	#=========================la $a2, centipedDirection # load the address of the array into $a2

# |this arr_loop is for draw each body in the centiped|
arr_loop:	#iterate over the loops elements to draw each body in the centiped
	lw $t1, 0($a1)		 # load a word from the centipedLocation array into $t1； |和下面”addi $a1, $a1, 4“连用|
	sw $t1, 0($t9)
	#=========================lw $t5, 0($a2)		 # load a word from the centipedDirection  array into $t5



	lw $t2, displayAddress  # $t2 stores the base address for display
	li $t3, 0xff0000	# $t3 stores the red colour code
	
	
	sll $t4,$t1, 2		# $t4 is the bias of the old body location in memory (offset*4); |往左移动2位等同于乘以4，所以这里 ”$t4 = 4 * $t1"|
	add $t4, $t2, $t4	# $t4 is the address of the old bug location；|这里用 $t2（displayaddress）+ $t4得到centipede在图中所对应的位置|
	sw $t3, 0($t4)		# paint the body with red；|把centipede的身子按顺序依次涂成红色|
	
	
	addi $a1, $a1, 4	 # increment $a1 by one, to point to the next element in the array; |$a1是centipedLocation array|
	addi $t9, $t9, 4
	#=========================addi $a2, $a2, 4	 # |$a2是centipedDirection array|
	
	addi $t5, $t5, -1	 # decrement $a3 by 1; |$a3是loop condition|=============更改了a3到t5
	bne $t5, $zero, arr_loop # |只要$a3不等于0， 就回到arr_loop，这样会导致所有的centipede body会被画出。|=============更改了a3到t5
	
	# |运行到这表明所有的 centipede的身子画完了，所以需要再画头部|
	lw $t1, 0($a1)		# |$t1保存了centipedeLocation[10]的值|
	sw $t1, 0($t9)
	
	
	# |在这之前，oldlocation都存好了|
	
	lw $t2, displayAddress
	li $t3, 0x00ff00	# |$t3保存了绿色|
	sll $t4,$t1, 2		# |往左移动2位等同于乘以4，所以这里 ”$t4 = 4 * $t1"|
	add $t4, $t2, $t4	# |这里用 $t2（displayaddress）+ $t4得到centipede头部在图中所对应的位置|
	sw $t3, 0($t4)		# |把centipede的头部涂成绿色|
	
	# |到这里centipede就画完了，接下来，我们需要先变换头部的address，再把之后的body部分的位置变成上一部分的位置| ============================================
		
		part_b:# |更新newlocation， 第0-9位的更新 （总共10个更新），第10位在后面（一个不更新）|
			addi $t5, $zero, 10	 # |$a3是loop count|
			add $t0, $zero, $zero	 # |i为t0|
			la $a2, centipedLocation # load the address of the “centipedLocation” array into $a1
		part_c:
			bge $t0,$t5,part_l 	 # |exit loop when i >= 10|
			sll $t2, $t0, 2
			add $t3, $a2, $t2 # |t3存储了第i位值|
			lw $t4, 4($t3)
			sw $t4, 0($t3)
		updatet:
			addi $t0, $t0, 1
			j part_c
		# |这上面的loop不会变|==============================================================================================
		
	part_l:	
		# | $a1
		# | 这里是加载关于 bottom 和 border 的一些信息的|
		la $t0, leftborder	
		lw $a2, 0($t0)		
		la $t3, rightborder
		lw $a3, 0($t3)
		la $t4, bottomcond # | 用bottomcond来判断每行是加还是减|
		lw $t5, 0($t4)
		
				
	part_check_mushrooms:
		# | 可用： （t2）, （t6）, （t7）, （t8） ,(t9),s1,s2 |
		# | $t1 是头的位置的值， 0($a1) 是 centipedLocation[10]的值. |
		# | 这个是用来根据 还存在的mushroom location来更改head的location |
		head_vs_mushrooms:
				addi $t2, $zero, 20	 # |$t2是loop count|
				la $t6, mushroomsLocation # | load the address of the array into $t6 |
				la $t9, mushroomsExist
			lp:
				lw $t8, 0($t6)	 # | $t8 存储了第i位蘑菇在“mushroomsLocation"中的值|
				lw $s1, 0($t9)    # | $t8 存储了第i位蘑菇在“mushroomsExist"中的值|
				# | 现在判断 蘑菇的值是否等于头的下一个值， 如是，则继续判断该蘑菇是不是没被消灭，如没被消灭，则根据”bottomcond“的值来跳转 |
			check_bottomcond1:
				beq $t5,$zero,check_head_equal_mushrooms_1
				j check_head_equal_mushrooms_2
			check_head_equal_mushrooms_1:
				# | 当direction往右的时候， 也就是 bottomcond = 0 |
				# | 判断蘑菇的值是否等于头的下一个值 |
				addi $t7, $t1,1		 # | $t7 是头的下一个位置|
				beq $t7,$t8,check_mushrooms_exist  # | 如果是进入下一个判断 |
				j update_lp			 # | 如果不是，则判断下个蘑菇 |
			check_head_equal_mushrooms_2:
				addi $t7, $t1,-1		 # | $t7 是头的下一个位置|
				beq $t7,$t8,check_mushrooms_exist  # | 如果是进入下一个判断 |
				j update_lp			 # | 如果不是，则判断下个蘑菇 |
			check_mushrooms_exist:
				# | 判断蘑菇是不是没被消灭 |
				bne $s1,$zero,check_bottomcond2  # | 如果没被消灭，则进入下个判断，会跳转到 ”check_bottomcond“当 $s1 是1的时候|
				j update_lp 			# | 如果被消灭，则判断下个蘑菇，这里只会在 $s1 是0的时候运行 |
			check_bottomcond2:
				# | 根据 bottomcond （$t5) 来判断跳到 part_g_1 还是 part_h_1 |
				beq $t5,$zero,part_g_1
				j part_h_1
			update_lp:
				# |下面四行确保loop能运行用的|
				addi $t6, $t6, 4
				addi $t9, $t9, 4
				addi $t2, $t2, -1	 
				bne $t2, $zero, lp
		
		# | 判断是否到了底部 |
		beq $t1,992,part_j
		beq $t1,1023,part_k
		j part_i
	part_k:# set bottomcond to 1
		addi $t5, $zero, 1
		sw $t5, 0($t4)
		j part_i
	part_j:# set bottomcod to 0
		addi $t5, $zero, 0
		sw $t5, 0($t4)		# |这里 $t4 是bottom condition |
	part_i:
		bne $a2,992,part_h
		bne $t5, 0, part_e
		j part_a
	part_h:	
		bne $t1, $a2,part_g # 头碰到左边的边界
	part_h_1:
		addi $t1, $t1, 32
		sw $t1, 0($a1)
		addi $a3, $a3,64
		sw $a3, 0($t3)   # |$t3 is rightborder|
		
		addi $t5, $zero, 0  # | 在这种情况下换行时， 让bottomcond 为 1 |
		sw $t5, 0($t4)
		j part_d
		
	part_g:
		bne $t1, $a3,part_f # 头碰到右边的边界 $a3
	part_g_1:
		addi $t1, $t1, 32
		sw $t1, 0($a1)
		addi $a2, $a2,64
		sw $a2, 0($t0)    # |$t0 is leftborder|
		
		addi $t5, $zero, 1 # | 在这种情况下换行时， 让bottomcond 为 1 |
		sw $t5, 0($t4)
		j part_d
		
		part_f: # |判断是 +1 还是 -1|
			slt $t5, $t1, $a3
			beq $t5,1,part_a
			j part_e
		
		part_a:
			# |如果头不在边界，改变head的position的rule|
			addi $t1, $t1, 1
			sw $t1, 0($a1)
			j part_d
		part_e:
			# |如果头不在边界，改变head的position的rule|
			addi $t1, $t1, -1
			sw $t1, 0($a1)
			j part_d
		
		part_d: # |把oldlocation的最后一位变成黑色|
			li $t3, 0x000000
			la $t9 oldlocation
			lw $t1, 0($t9)
			
			
			
			lw $t2, displayAddress 
			sll $t4,$t1, 2	
			add $t4, $t2, $t4	
			sw $t3, 0($t4)
			
	#========================================================================================================================================================
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	jr $ra # |回到main|

# function to detect any keystroke
check_keystroke:
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	lw $t8, 0xffff0000
	beq $t8, 1, get_keyboard_input # if key is pressed, jump to get this key
	addi $t8, $zero, 0 # |清空$t8|
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra
	
# function to get the input key
get_keyboard_input:
	# move stack pointer a work and push ra onto it; |用stack存储check_keystroke的地址，以用于之后的jr|
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	lw $t2, 0xffff0004
	beq $t2, 0x6A, respond_to_j
	beq $t2, 0x6B, respond_to_k
	beq $t2, 0x78, respond_to_x
	beq $t2, 0x73, respond_to_s
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra # |回到check_keystroke|
	
# Call back function of j key
respond_to_j:
	# |用stack存储check_keystroke的地址，以用于之后的jr|
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	#==================================================================================================================	
	
	la $t0, bugLocation	# load the address of buglocation from memory
	lw $t1, 0($t0)		# load the bug location itself in t1
	
	lw $t2, displayAddress  # $t2 stores the base address for display
	li $t3, 0x000000	# $t3 stores the black colour code
	
	# |把原来bug blaster所在的位置涂成黑色|
	sll $t4,$t1, 2		# $t4 the bias of the old buglocation；|往左移动两位（logically）相当于乘以4（联想11往左移动两位是1100，表示从3变成了12）|
	add $t4, $t2, $t4	# $t4 is the address of the old bug location；|$t4是原来bug blaster的位置|
	sw $t3, 0($t4)		# paint the first (top-left) unit black.
	
	beq $t1, 992, skip_movement # prevent the bug from getting out of the canvas； 改800 到 992
	
	addi $t1, $t1, -1	# move the bug one location to the right；|这里$t1是新的bug blaster的位置|
skip_movement:
	sw $t1, 0($t0)		# save the bug location; |$t0是bugblaster的location|

	li $t3, 0xffffff	# $t3 stores the white colour code
	
	# |把现在bug blaster所在的位置涂成白色|
	sll $t4,$t1, 2
	add $t4, $t2, $t4
	sw $t3, 0($t4)		# paint the first (top-left) unit white.
	
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra # |回到get_keyboard_input|

# Call back function of k key
respond_to_k:
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	la $t0, bugLocation	# load the address of buglocation from memory
	lw $t1, 0($t0)		# load the bug location itself in t1
	
	lw $t2, displayAddress  # $t2 stores the base address for display
	li $t3, 0x000000	# $t3 stores the black colour code
	
	sll $t4,$t1, 2		# $t4 the bias of the old buglocation
	add $t4, $t2, $t4	# $t4 is the address of the old bug location
	sw $t3, 0($t4)		# paint the block with black
	
	beq $t1, 1023, skip_movement2 #prevent the bug from getting out of the canvas； 改831 到 1023
	addi $t1, $t1, 1	# move the bug one location to the right
skip_movement2:
	sw $t1, 0($t0)		# save the bug location

	li $t3, 0xffffff	# $t3 stores the white colour code
	
	sll $t4,$t1, 2
	add $t4, $t2, $t4
	sw $t3, 0($t4)		# paint the block with white
	
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra
	
respond_to_x: #|需要增加射击子弹的code|
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	#=====================================================================================
	# | 现在要射出子弹，所有的存储都可以用 |
	
	# | 关于子弹的信息 |
	la $t0, bugLocation	# load the address of buglocation from memory
	lw $t1, 0($t0)		# load the bug location itself in t1
	addi $t1, $t1, -32      # | 子弹应该从 buglocation 的正上方射出， 所以减去 32， 在这之后 $t1 就是子弹的虚假位置 |
	
	# | 一个运行31次的loop，用来判断子弹的轨迹，以及改变centipedLife，mushroomsExit，mushroomsLife |
	addi $t8, $zero, 0 # | $t8 是 i |
	addi $t9,$zero,31 # | exit condition 是当 i 为 31的时候 |
	x_loop:
		jal delay2
		# | 得到每个list里第i个element的index |
		beq $t8,$t9,x_exit  # | 如果i等于31，则exit这个for loop |
		sll $s0, $t8, 2     # | 现在 s0 代表了每个list里面的第 i 个 element 的 index |
		lw $s1, displayAddress
		
		# | 先画出现在子弹在哪 |
		bne $t8,$zero,paint_last_black # | 只有第一次loop不会把子弹的上一格给变黑 |
		li $t2, 0xffffff        # | 子弹的颜色是白色 |
		sll $s3,$t1,2		# | $s3是子弹的真实位置,还没加上基础位置 |
		add $s3,$s1,$s3		# | 加上基础位置 |
		sw $t2,0($s3)		# | 给那个子弹位置上色 |
		j x_checking
	paint_last_black:
		# | 先把old子弹位置画成黑色 |
		li $t2, 0x000000  # |黑色|
		addi $s3,$t1, 32 	# | s3 是子弹的上一个位置 |
		sll $s3, $s3, 2
		add $s3,$s1,$s3		# | 加上基础位置 |
		sw $t2,0($s3)		# | 给老子弹位置上黑色 |
		# | 再把新子弹的位置画成白色 |
		li $t2, 0xffffff        # | 子弹的颜色是白色 |
		sll $s3,$t1,2		# | $s3是子弹的真实位置,还没加上基础位置 |
		add $s3,$s1,$s3		# | 加上基础位置 |
		sw $t2,0($s3)		# | 给那个子弹位置上色 |
		
		beq $t8,30,paint_present_black
		j x_checking
	paint_present_black:
		li $t2, 0x000000        # | 黑色 |
		sll $s3,$t1,2		# | $s3是子弹的真实位置,还没加上基础位置 |
		add $s3,$s1,$s3		# | 加上基础位置 |
		sw $t2,0($s3)		# | 给那个子弹位置上色 |
		j x_update
		# | 再判断子弹的下一个位置是否接触到了mushrooms 或者 centipede |
	x_checking:
		# | 先判断子弹的下一个位置是不是蘑菇， 需要一个loop 20 次的来判断 |
		check_next_touch_mushroom:
			addi $s3,$t1, -32 # | s3 是子弹的下一个位置 |
			# | 关于蘑菇的信息 |
			la $t3, mushroomsLocation
			la $t4, mushroomsExist
			la $t5, mushroomsLife
		
			addi $s4, $zero,0	# | s4 是 n |
			addi $s5, $zero,20	# | loop exit cond is 20 |
		mushroom_loop:
			beq $s4,$s5,check_next_touch_centipede	# | for loop head |
			sll $s6,$s4,2
			
			# | 先判断子弹的下一个位置有没有蘑菇 |
			add $s6,$s6,$t3
			lw $s7,0($s6)   # | s7是第i个蘑菇的位置的值 |
			beq $s3,$s7,check_mushroomExit # | 如果子弹的下一个位置是蘑菇，跳转到check_mushroomExit |
			j mushroom_update
		check_mushroomExit:
			sll $s6, $s4, 2
			add $s6,$s6,$t4
			lw $s7, 0($s6)
			beq $s7,1,change_mushroom
		mushroom_update:
			addi $s4,$s4,1
			j mushroom_loop
		change_mushroom:
			# | 先让子弹消失，即子弹现在位置涂黑 |
			li $t2, 0x000000        # | 黑色 |
			sll $s3,$t1,2		# | $s3是子弹的真实位置,还没加上基础位置 |
			add $s3,$s1,$s3		# | 加上基础位置 |
			sw $t2,0($s3)		# | 给那个子弹位置上色 |
			
			# | 改变蘑菇的生命值， 如果生命值在改变后为0，则改变蘑菇exit的值为0 |
			sll $s6, $s4, 2
			add $s6, $s6, $t5
			lw $s7, 0($s6)
			addi $s7, $s7,-1
			sw $s7, 0($s6)	# | 存储改变后的蘑菇生命值 |

			bne $s7, $zero, x_exit # | 如果改变后的life不为0，则直接exit |
			
			sll $s6, $s4, 2		# | 如果改变后的life为0，则先改变mushroomsExist |
			add $s6, $s6, $t4
			sw $zero, 0($s6) 
			
			sll $s6, $s4, 2		# | 再改变mushrooms的颜色，然后退出 |
			add $s6, $s6, $t3
			lw $s7, 0($s6)
			sll $s7,$s7,2
			add $s7, $s7, $s1
			sw $t2, 0($s7) 
			
			j x_exit # | 因为碰到了蘑菇，所以子弹消失，即所有loop 结束 |
			
		# | 再判断子弹的下一个位置是不是蜈蚣， 需要一个loop 11 次的来判断 |
		check_next_touch_centipede:
			addi $s3,$t1, -32 # | s3 是子弹的下一个位置 |
			# | 关于centipede的信息 |
			la $t6, centipedLocation
			la $t7, centipedLife
		
			addi $s4, $zero,0	# | s4 是 n |
			addi $s5, $zero,11	# | loop exit cond is 20 |
		centipede_loop:
			beq $s4,$s5,x_update
			sll $s6,$s4,2
			
			# | 先判断子弹的下一个位置有没有蘑菇 |
			add $s6,$s6,$t6
			lw $s7,0($s6)   # | s7是第i个centipede身体的位置的值 |
			beq $s3,$s7,check_centipedeLife # | 如果子弹的下一个位置是centipede身体，跳转到check_centipedeLife |
			j centipede_update
		check_centipedeLife:
			lw $s7, 0($t7)
			bne $s7,0,change_centipede
			j x_update	# | 如果centipede的生命为0，则跳转到 x_update |
		centipede_update:
			addi $s4,$s4,1
			j centipede_loop
		change_centipede:
			# | 先让子弹消失，即子弹现在位置涂黑 |
			li $t2, 0x000000        # | 黑色 |
			sll $s3,$t1,2		# | $s3是子弹的真实位置,还没加上基础位置 |
			add $s3,$s1,$s3		# | 加上基础位置 |
			sw $t2,0($s3)		# | 给那个子弹位置上色 |
		
			# | centipede的生命减少1 |
			addi $s7, $s7, -1
			sw $s7,0($t7)
			
			# | 如果减少后的centipede的生命值为0，则把所有centipede涂成黑色 |
			bne $s7, $zero, x_exit
			addi $t3,$zero,0     # | i 的值 |
			addi $t4, $zero,11 
			change_centipede_loop:
				beq $t3, $t4, x_exit
				sll $s3, $t3, 2
				add $s3, $s3,$t6
				lw $s7, 0($s3)
				sll $s7, $s7, 2
				add $s7, $s7, $s1
				sw $t2, 0($s7)
			change_centipede_updata:
				addi $t3, $t3, 1
				j change_centipede_loop
		
	x_update:
		addi $t1, $t1, -32 	# | 更新子弹位置 |
		addi $t8, $t8, 1	# | 更新i |
		j x_loop
	x_exit:
	#=====================================================================================
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	jr $ra
	
respond_to_s: 
	# |这个应该是用来终止游戏的，即”Game over / retry option“，大概率是直接jump到”exit“|
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	j Exit  # | 一旦接收到 s 就结束 |
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra

delay: # |暂时不知道干啥的|
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	li $a1, 25000 # change a2 to a1
delay1:
	addi $a1, $a1, -1 # change a2 to a1
	bgtz $a1, delay1 # change a2 to a1
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra

delay2: # |暂时不知道干啥的|
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	li $a1, 1000 # change a2 to a1
delay2_1:
	addi $a1, $a1, -1 # change a2 to a1
	bgtz $a1, delay2_1 # change a2 to a1
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra

get_random_number: # |在11 到 928 之间 （inclusive）|
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $t3, $0, 10
	li $v0, 42         # Service 42, random int bounded
  	li $a0, 0          # Select random generator 0
  	li $a1, 928        # |蘑菇只会生成在第0行到第29行, 29*32=928|     
  	syscall             # Generate random int (returns in $a0)
  	
  	bge $t3,$a0,get_random_number  # | 只要 10 大于 random number 则重新创建一个random number |
  	
  	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
  	jr $ra
random_mushrooms: # |随机生成20个蘑菇|
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $t2, $zero, 20	 # |$a2是loop count|
	add $t1, $zero, $zero	 # |i为t0|
	la $a2, mushroomsLocation # |load the address of the “mushrroms” array into $a2|
	part_for_body:
		bge $t1,$t2,part_exit_loop 	 # |exit loop when i >= 20。 也就是说更新所有的蘑菇位置|
		jal get_random_number   # |random number 存储在 $a0 |
		
		sll $t3, $t1, 2		# | array 中存储的位置是从0，4，8这样存的|
		add $t4, $a2, $t3 	# |t4存储了第i位值|
		
		sw $a0, 0($t4)
		
		lw $t5, 0($t4)
		addi $v0,$zero,1
		add $a0, $0, $t5
		syscall
		
	updatet_1:
		addi $t1, $t1, 1
		j part_for_body
	part_exit_loop:
		lw $ra, 0($sp)
		addi $sp, $sp, 4
	
  		jr $ra
 draw_mushrooms:
 	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $t0, $zero, 20	 # |$t0是loop count|
	la $a1, mushroomsLocation # load the address of the array into $a1
	painting_mushrooms:
		lw $t1, 0($a1)	 # | $t1 存储了第i位蘑菇在“mushroomsLocation"中的值|
		
		addi $v0,$zero,1
		add $a0,$zero,$t1
		syscall

		li $t2, 0xff4dc3 # | $t2 存储了蘑菇颜色, 是粉色|
		lw $t3, displayAddress
		sll $t4,$t1,2	 # | $t4 = 4*$t1|
		add $t4, $t3, $t4 # | 得到基于 ”displayAddress“ 的 第 i个mushroom的位置|
		sw $t2, 0($t4)	  # | 给第i个蘑菇上色|

		# |下面三行确保loop能运行用的|
		addi $a1, $a1, 4
		addi $t0, $t0, -1	 
		bne $t0, $zero, painting_mushrooms
	exit_part:
		lw $ra, 0($sp)
		addi $sp, $sp, 4
	
  		jr $ra