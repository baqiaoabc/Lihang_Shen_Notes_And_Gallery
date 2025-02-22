.data
	displayAddress:	.word 0x10008000
	#player
	bugLocation: .word 1007  #|change the original one to make sure bug bluster at the bottom of the screen������992 �� 1023|
	dartLocation: .word 975  # | �ӵ���λ���� player �������� |
	#centiped
	centipedLocation: .word 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10   # |10 is the head, body is red, head is green|
	centipedLife: .word 3   		# | ��lifeΪ0��ʱ��centiped���� |
	
	oldlocation: 	  .word 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
	leftborder: .word -32#0
	rightborder: .word 31
	bottomcond: .word 0  # | 1��ʱ��-1��0��ʱ��+1 , ���ﲻ����ֻ����bottom line |
	#mushrooms
	mushroomsLocation: .word 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19  # |������20��Ģ��|
	mushroomsExist: .word 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1     # | 1��ʾĢ�����ڣ�0��ʾĢ�������� |
	mushroomsLife: .word 3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3 	# | ��lifeΪ0��ʱ��mushrooms ���� |
	#================================================================================centipedDirection: .word 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1   #������һλ����Ϊ������һ��ͷ��
.text 

Main:
	jal random_mushrooms # |�������20mushrooms��location���롰mushroomsLocation��|
	jal draw_mushrooms   # |����ÿһ��Ģ��|
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
	
	addi $t5, $zero, 10	 # |$a3��loop count|=============������a3��t5,
	la $a1, centipedLocation # load the address of the array into $a1
	la $t9, oldlocation
	
	#=========================la $a2, centipedDirection # load the address of the array into $a2

# |this arr_loop is for draw each body in the centiped|
arr_loop:	#iterate over the loops elements to draw each body in the centiped
	lw $t1, 0($a1)		 # load a word from the centipedLocation array into $t1�� |�����桱addi $a1, $a1, 4������|
	sw $t1, 0($t9)
	#=========================lw $t5, 0($a2)		 # load a word from the centipedDirection  array into $t5



	lw $t2, displayAddress  # $t2 stores the base address for display
	li $t3, 0xff0000	# $t3 stores the red colour code
	
	
	sll $t4,$t1, 2		# $t4 is the bias of the old body location in memory (offset*4); |�����ƶ�2λ��ͬ�ڳ���4���������� ��$t4 = 4 * $t1"|
	add $t4, $t2, $t4	# $t4 is the address of the old bug location��|������ $t2��displayaddress��+ $t4�õ�centipede��ͼ������Ӧ��λ��|
	sw $t3, 0($t4)		# paint the body with red��|��centipede�����Ӱ�˳������Ϳ�ɺ�ɫ|
	
	
	addi $a1, $a1, 4	 # increment $a1 by one, to point to the next element in the array; |$a1��centipedLocation array|
	addi $t9, $t9, 4
	#=========================addi $a2, $a2, 4	 # |$a2��centipedDirection array|
	
	addi $t5, $t5, -1	 # decrement $a3 by 1; |$a3��loop condition|=============������a3��t5
	bne $t5, $zero, arr_loop # |ֻҪ$a3������0�� �ͻص�arr_loop�������ᵼ�����е�centipede body�ᱻ������|=============������a3��t5
	
	# |���е���������е� centipede�����ӻ����ˣ�������Ҫ�ٻ�ͷ��|
	lw $t1, 0($a1)		# |$t1������centipedeLocation[10]��ֵ|
	sw $t1, 0($t9)
	
	
	# |����֮ǰ��oldlocation�������|
	
	lw $t2, displayAddress
	li $t3, 0x00ff00	# |$t3��������ɫ|
	sll $t4,$t1, 2		# |�����ƶ�2λ��ͬ�ڳ���4���������� ��$t4 = 4 * $t1"|
	add $t4, $t2, $t4	# |������ $t2��displayaddress��+ $t4�õ�centipedeͷ����ͼ������Ӧ��λ��|
	sw $t3, 0($t4)		# |��centipede��ͷ��Ϳ����ɫ|
	
	# |������centipede�ͻ����ˣ���������������Ҫ�ȱ任ͷ����address���ٰ�֮���body���ֵ�λ�ñ����һ���ֵ�λ��| ============================================
		
		part_b:# |����newlocation�� ��0-9λ�ĸ��� ���ܹ�10�����£�����10λ�ں��棨һ�������£�|
			addi $t5, $zero, 10	 # |$a3��loop count|
			add $t0, $zero, $zero	 # |iΪt0|
			la $a2, centipedLocation # load the address of the ��centipedLocation�� array into $a1
		part_c:
			bge $t0,$t5,part_l 	 # |exit loop when i >= 10|
			sll $t2, $t0, 2
			add $t3, $a2, $t2 # |t3�洢�˵�iλֵ|
			lw $t4, 4($t3)
			sw $t4, 0($t3)
		updatet:
			addi $t0, $t0, 1
			j part_c
		# |�������loop�����|==============================================================================================
		
	part_l:	
		# | $a1
		# | �����Ǽ��ع��� bottom �� border ��һЩ��Ϣ��|
		la $t0, leftborder	
		lw $a2, 0($t0)		
		la $t3, rightborder
		lw $a3, 0($t3)
		la $t4, bottomcond # | ��bottomcond���ж�ÿ���Ǽӻ��Ǽ�|
		lw $t5, 0($t4)
		
				
	part_check_mushrooms:
		# | ���ã� ��t2��, ��t6��, ��t7��, ��t8�� ,(t9),s1,s2 |
		# | $t1 ��ͷ��λ�õ�ֵ�� 0($a1) �� centipedLocation[10]��ֵ. |
		# | ������������� �����ڵ�mushroom location������head��location |
		head_vs_mushrooms:
				addi $t2, $zero, 20	 # |$t2��loop count|
				la $t6, mushroomsLocation # | load the address of the array into $t6 |
				la $t9, mushroomsExist
			lp:
				lw $t8, 0($t6)	 # | $t8 �洢�˵�iλĢ���ڡ�mushroomsLocation"�е�ֵ|
				lw $s1, 0($t9)    # | $t8 �洢�˵�iλĢ���ڡ�mushroomsExist"�е�ֵ|
				# | �����ж� Ģ����ֵ�Ƿ����ͷ����һ��ֵ�� ���ǣ�������жϸ�Ģ���ǲ���û��������û����������ݡ�bottomcond����ֵ����ת |
			check_bottomcond1:
				beq $t5,$zero,check_head_equal_mushrooms_1
				j check_head_equal_mushrooms_2
			check_head_equal_mushrooms_1:
				# | ��direction���ҵ�ʱ�� Ҳ���� bottomcond = 0 |
				# | �ж�Ģ����ֵ�Ƿ����ͷ����һ��ֵ |
				addi $t7, $t1,1		 # | $t7 ��ͷ����һ��λ��|
				beq $t7,$t8,check_mushrooms_exist  # | ����ǽ�����һ���ж� |
				j update_lp			 # | ������ǣ����ж��¸�Ģ�� |
			check_head_equal_mushrooms_2:
				addi $t7, $t1,-1		 # | $t7 ��ͷ����һ��λ��|
				beq $t7,$t8,check_mushrooms_exist  # | ����ǽ�����һ���ж� |
				j update_lp			 # | ������ǣ����ж��¸�Ģ�� |
			check_mushrooms_exist:
				# | �ж�Ģ���ǲ���û������ |
				bne $s1,$zero,check_bottomcond2  # | ���û������������¸��жϣ�����ת�� ��check_bottomcond���� $s1 ��1��ʱ��|
				j update_lp 			# | ������������ж��¸�Ģ��������ֻ���� $s1 ��0��ʱ������ |
			check_bottomcond2:
				# | ���� bottomcond ��$t5) ���ж����� part_g_1 ���� part_h_1 |
				beq $t5,$zero,part_g_1
				j part_h_1
			update_lp:
				# |��������ȷ��loop�������õ�|
				addi $t6, $t6, 4
				addi $t9, $t9, 4
				addi $t2, $t2, -1	 
				bne $t2, $zero, lp
		
		# | �ж��Ƿ��˵ײ� |
		beq $t1,992,part_j
		beq $t1,1023,part_k
		j part_i
	part_k:# set bottomcond to 1
		addi $t5, $zero, 1
		sw $t5, 0($t4)
		j part_i
	part_j:# set bottomcod to 0
		addi $t5, $zero, 0
		sw $t5, 0($t4)		# |���� $t4 ��bottom condition |
	part_i:
		bne $a2,992,part_h
		bne $t5, 0, part_e
		j part_a
	part_h:	
		bne $t1, $a2,part_g # ͷ������ߵı߽�
	part_h_1:
		addi $t1, $t1, 32
		sw $t1, 0($a1)
		addi $a3, $a3,64
		sw $a3, 0($t3)   # |$t3 is rightborder|
		
		addi $t5, $zero, 0  # | ����������»���ʱ�� ��bottomcond Ϊ 1 |
		sw $t5, 0($t4)
		j part_d
		
	part_g:
		bne $t1, $a3,part_f # ͷ�����ұߵı߽� $a3
	part_g_1:
		addi $t1, $t1, 32
		sw $t1, 0($a1)
		addi $a2, $a2,64
		sw $a2, 0($t0)    # |$t0 is leftborder|
		
		addi $t5, $zero, 1 # | ����������»���ʱ�� ��bottomcond Ϊ 1 |
		sw $t5, 0($t4)
		j part_d
		
		part_f: # |�ж��� +1 ���� -1|
			slt $t5, $t1, $a3
			beq $t5,1,part_a
			j part_e
		
		part_a:
			# |���ͷ���ڱ߽磬�ı�head��position��rule|
			addi $t1, $t1, 1
			sw $t1, 0($a1)
			j part_d
		part_e:
			# |���ͷ���ڱ߽磬�ı�head��position��rule|
			addi $t1, $t1, -1
			sw $t1, 0($a1)
			j part_d
		
		part_d: # |��oldlocation�����һλ��ɺ�ɫ|
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
	jr $ra # |�ص�main|

# function to detect any keystroke
check_keystroke:
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	lw $t8, 0xffff0000
	beq $t8, 1, get_keyboard_input # if key is pressed, jump to get this key
	addi $t8, $zero, 0 # |���$t8|
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra
	
# function to get the input key
get_keyboard_input:
	# move stack pointer a work and push ra onto it; |��stack�洢check_keystroke�ĵ�ַ��������֮���jr|
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
	
	jr $ra # |�ص�check_keystroke|
	
# Call back function of j key
respond_to_j:
	# |��stack�洢check_keystroke�ĵ�ַ��������֮���jr|
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	#==================================================================================================================	
	
	la $t0, bugLocation	# load the address of buglocation from memory
	lw $t1, 0($t0)		# load the bug location itself in t1
	
	lw $t2, displayAddress  # $t2 stores the base address for display
	li $t3, 0x000000	# $t3 stores the black colour code
	
	# |��ԭ��bug blaster���ڵ�λ��Ϳ�ɺ�ɫ|
	sll $t4,$t1, 2		# $t4 the bias of the old buglocation��|�����ƶ���λ��logically���൱�ڳ���4������11�����ƶ���λ��1100����ʾ��3�����12��|
	add $t4, $t2, $t4	# $t4 is the address of the old bug location��|$t4��ԭ��bug blaster��λ��|
	sw $t3, 0($t4)		# paint the first (top-left) unit black.
	
	beq $t1, 992, skip_movement # prevent the bug from getting out of the canvas�� ��800 �� 992
	
	addi $t1, $t1, -1	# move the bug one location to the right��|����$t1���µ�bug blaster��λ��|
skip_movement:
	sw $t1, 0($t0)		# save the bug location; |$t0��bugblaster��location|

	li $t3, 0xffffff	# $t3 stores the white colour code
	
	# |������bug blaster���ڵ�λ��Ϳ�ɰ�ɫ|
	sll $t4,$t1, 2
	add $t4, $t2, $t4
	sw $t3, 0($t4)		# paint the first (top-left) unit white.
	
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra # |�ص�get_keyboard_input|

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
	
	beq $t1, 1023, skip_movement2 #prevent the bug from getting out of the canvas�� ��831 �� 1023
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
	
respond_to_x: #|��Ҫ��������ӵ���code|
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	#=====================================================================================
	# | ����Ҫ����ӵ������еĴ洢�������� |
	
	# | �����ӵ�����Ϣ |
	la $t0, bugLocation	# load the address of buglocation from memory
	lw $t1, 0($t0)		# load the bug location itself in t1
	addi $t1, $t1, -32      # | �ӵ�Ӧ�ô� buglocation �����Ϸ������ ���Լ�ȥ 32�� ����֮�� $t1 �����ӵ������λ�� |
	
	# | һ������31�ε�loop�������ж��ӵ��Ĺ켣���Լ��ı�centipedLife��mushroomsExit��mushroomsLife |
	addi $t8, $zero, 0 # | $t8 �� i |
	addi $t9,$zero,31 # | exit condition �ǵ� i Ϊ 31��ʱ�� |
	x_loop:
		jal delay2
		# | �õ�ÿ��list���i��element��index |
		beq $t8,$t9,x_exit  # | ���i����31����exit���for loop |
		sll $s0, $t8, 2     # | ���� s0 ������ÿ��list����ĵ� i �� element �� index |
		lw $s1, displayAddress
		
		# | �Ȼ��������ӵ����� |
		bne $t8,$zero,paint_last_black # | ֻ�е�һ��loop������ӵ�����һ������ |
		li $t2, 0xffffff        # | �ӵ�����ɫ�ǰ�ɫ |
		sll $s3,$t1,2		# | $s3���ӵ�����ʵλ��,��û���ϻ���λ�� |
		add $s3,$s1,$s3		# | ���ϻ���λ�� |
		sw $t2,0($s3)		# | ���Ǹ��ӵ�λ����ɫ |
		j x_checking
	paint_last_black:
		# | �Ȱ�old�ӵ�λ�û��ɺ�ɫ |
		li $t2, 0x000000  # |��ɫ|
		addi $s3,$t1, 32 	# | s3 ���ӵ�����һ��λ�� |
		sll $s3, $s3, 2
		add $s3,$s1,$s3		# | ���ϻ���λ�� |
		sw $t2,0($s3)		# | �����ӵ�λ���Ϻ�ɫ |
		# | �ٰ����ӵ���λ�û��ɰ�ɫ |
		li $t2, 0xffffff        # | �ӵ�����ɫ�ǰ�ɫ |
		sll $s3,$t1,2		# | $s3���ӵ�����ʵλ��,��û���ϻ���λ�� |
		add $s3,$s1,$s3		# | ���ϻ���λ�� |
		sw $t2,0($s3)		# | ���Ǹ��ӵ�λ����ɫ |
		
		beq $t8,30,paint_present_black
		j x_checking
	paint_present_black:
		li $t2, 0x000000        # | ��ɫ |
		sll $s3,$t1,2		# | $s3���ӵ�����ʵλ��,��û���ϻ���λ�� |
		add $s3,$s1,$s3		# | ���ϻ���λ�� |
		sw $t2,0($s3)		# | ���Ǹ��ӵ�λ����ɫ |
		j x_update
		# | ���ж��ӵ�����һ��λ���Ƿ�Ӵ�����mushrooms ���� centipede |
	x_checking:
		# | ���ж��ӵ�����һ��λ���ǲ���Ģ���� ��Ҫһ��loop 20 �ε����ж� |
		check_next_touch_mushroom:
			addi $s3,$t1, -32 # | s3 ���ӵ�����һ��λ�� |
			# | ����Ģ������Ϣ |
			la $t3, mushroomsLocation
			la $t4, mushroomsExist
			la $t5, mushroomsLife
		
			addi $s4, $zero,0	# | s4 �� n |
			addi $s5, $zero,20	# | loop exit cond is 20 |
		mushroom_loop:
			beq $s4,$s5,check_next_touch_centipede	# | for loop head |
			sll $s6,$s4,2
			
			# | ���ж��ӵ�����һ��λ����û��Ģ�� |
			add $s6,$s6,$t3
			lw $s7,0($s6)   # | s7�ǵ�i��Ģ����λ�õ�ֵ |
			beq $s3,$s7,check_mushroomExit # | ����ӵ�����һ��λ����Ģ������ת��check_mushroomExit |
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
			# | �����ӵ���ʧ�����ӵ�����λ��Ϳ�� |
			li $t2, 0x000000        # | ��ɫ |
			sll $s3,$t1,2		# | $s3���ӵ�����ʵλ��,��û���ϻ���λ�� |
			add $s3,$s1,$s3		# | ���ϻ���λ�� |
			sw $t2,0($s3)		# | ���Ǹ��ӵ�λ����ɫ |
			
			# | �ı�Ģ��������ֵ�� �������ֵ�ڸı��Ϊ0����ı�Ģ��exit��ֵΪ0 |
			sll $s6, $s4, 2
			add $s6, $s6, $t5
			lw $s7, 0($s6)
			addi $s7, $s7,-1
			sw $s7, 0($s6)	# | �洢�ı���Ģ������ֵ |

			bne $s7, $zero, x_exit # | ����ı���life��Ϊ0����ֱ��exit |
			
			sll $s6, $s4, 2		# | ����ı���lifeΪ0�����ȸı�mushroomsExist |
			add $s6, $s6, $t4
			sw $zero, 0($s6) 
			
			sll $s6, $s4, 2		# | �ٸı�mushrooms����ɫ��Ȼ���˳� |
			add $s6, $s6, $t3
			lw $s7, 0($s6)
			sll $s7,$s7,2
			add $s7, $s7, $s1
			sw $t2, 0($s7) 
			
			j x_exit # | ��Ϊ������Ģ���������ӵ���ʧ��������loop ���� |
			
		# | ���ж��ӵ�����һ��λ���ǲ�����򼣬 ��Ҫһ��loop 11 �ε����ж� |
		check_next_touch_centipede:
			addi $s3,$t1, -32 # | s3 ���ӵ�����һ��λ�� |
			# | ����centipede����Ϣ |
			la $t6, centipedLocation
			la $t7, centipedLife
		
			addi $s4, $zero,0	# | s4 �� n |
			addi $s5, $zero,11	# | loop exit cond is 20 |
		centipede_loop:
			beq $s4,$s5,x_update
			sll $s6,$s4,2
			
			# | ���ж��ӵ�����һ��λ����û��Ģ�� |
			add $s6,$s6,$t6
			lw $s7,0($s6)   # | s7�ǵ�i��centipede�����λ�õ�ֵ |
			beq $s3,$s7,check_centipedeLife # | ����ӵ�����һ��λ����centipede���壬��ת��check_centipedeLife |
			j centipede_update
		check_centipedeLife:
			lw $s7, 0($t7)
			bne $s7,0,change_centipede
			j x_update	# | ���centipede������Ϊ0������ת�� x_update |
		centipede_update:
			addi $s4,$s4,1
			j centipede_loop
		change_centipede:
			# | �����ӵ���ʧ�����ӵ�����λ��Ϳ�� |
			li $t2, 0x000000        # | ��ɫ |
			sll $s3,$t1,2		# | $s3���ӵ�����ʵλ��,��û���ϻ���λ�� |
			add $s3,$s1,$s3		# | ���ϻ���λ�� |
			sw $t2,0($s3)		# | ���Ǹ��ӵ�λ����ɫ |
		
			# | centipede����������1 |
			addi $s7, $s7, -1
			sw $s7,0($t7)
			
			# | ������ٺ��centipede������ֵΪ0���������centipedeͿ�ɺ�ɫ |
			bne $s7, $zero, x_exit
			addi $t3,$zero,0     # | i ��ֵ |
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
		addi $t1, $t1, -32 	# | �����ӵ�λ�� |
		addi $t8, $t8, 1	# | ����i |
		j x_loop
	x_exit:
	#=====================================================================================
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	jr $ra
	
respond_to_s: 
	# |���Ӧ����������ֹ��Ϸ�ģ�����Game over / retry option�����������ֱ��jump����exit��|
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	j Exit  # | һ�����յ� s �ͽ��� |
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra

delay: # |��ʱ��֪����ɶ��|
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

delay2: # |��ʱ��֪����ɶ��|
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

get_random_number: # |��11 �� 928 ֮�� ��inclusive��|
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $t3, $0, 10
	li $v0, 42         # Service 42, random int bounded
  	li $a0, 0          # Select random generator 0
  	li $a1, 928        # |Ģ��ֻ�������ڵ�0�е���29��, 29*32=928|     
  	syscall             # Generate random int (returns in $a0)
  	
  	bge $t3,$a0,get_random_number  # | ֻҪ 10 ���� random number �����´���һ��random number |
  	
  	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
  	jr $ra
random_mushrooms: # |�������20��Ģ��|
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $t2, $zero, 20	 # |$a2��loop count|
	add $t1, $zero, $zero	 # |iΪt0|
	la $a2, mushroomsLocation # |load the address of the ��mushrroms�� array into $a2|
	part_for_body:
		bge $t1,$t2,part_exit_loop 	 # |exit loop when i >= 20�� Ҳ����˵�������е�Ģ��λ��|
		jal get_random_number   # |random number �洢�� $a0 |
		
		sll $t3, $t1, 2		# | array �д洢��λ���Ǵ�0��4��8�������|
		add $t4, $a2, $t3 	# |t4�洢�˵�iλֵ|
		
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
	
	addi $t0, $zero, 20	 # |$t0��loop count|
	la $a1, mushroomsLocation # load the address of the array into $a1
	painting_mushrooms:
		lw $t1, 0($a1)	 # | $t1 �洢�˵�iλĢ���ڡ�mushroomsLocation"�е�ֵ|
		
		addi $v0,$zero,1
		add $a0,$zero,$t1
		syscall

		li $t2, 0xff4dc3 # | $t2 �洢��Ģ����ɫ, �Ƿ�ɫ|
		lw $t3, displayAddress
		sll $t4,$t1,2	 # | $t4 = 4*$t1|
		add $t4, $t3, $t4 # | �õ����� ��displayAddress�� �� �� i��mushroom��λ��|
		sw $t2, 0($t4)	  # | ����i��Ģ����ɫ|

		# |��������ȷ��loop�������õ�|
		addi $a1, $a1, 4
		addi $t0, $t0, -1	 
		bne $t0, $zero, painting_mushrooms
	exit_part:
		lw $ra, 0($sp)
		addi $sp, $sp, 4
	
  		jr $ra