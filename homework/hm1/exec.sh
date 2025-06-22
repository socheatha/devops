#!/bin/bash
#
#@topic: Home work #1, a shellscript to install zsh cmd, oh my zsh and relavant plugins...
#@author: Socheatha TEY
#@date: 2025-05-02

#print title out
echo "-------------------------------------------------"
echo "*** Installing Oh My Zsh for user: $(whoami) ***"
echo ""
sleep 1

#validate require sudoer
if sudo echo $(groups) | grep -q "sudo"; then
	
	#1)-Installing ZSH command
	echo -n "** Checking ZSH status: "
	sleep 0.5
	if which zsh > /dev/null 2>&1; then
		echo "already installed."
	else
		echo -n "installing ..."
		sudo apt-get install zsh -y > /dev/null
		echo "...done"
	fi
	#END install ZSH command




	#2)-Installing Oh My Zsh
	oh_my_zsh_src="https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh"
	oh_my_zsh_des="$HOME/.oh-my-zsh"

	echo -n "** Checking Oh My Zsh status: "
	sleep 0.5
	if ls $oh_my_zsh_des > /dev/null 2>&1; then
		echo "already installed."
	else
		echo -n "installing ..."
		sudo yes | sh -c "$(curl -fsSL $oh_my_zsh_src)" > /dev/null 2>&1
		echo "...done"
	fi
	#END install Oh My Zsh




	#3)-Installing other plugins 
	#(Note: variable $ZSH_CUSTOM return empty value, I bypass to "$HOME/.oh-my-zsh/custom" and will ask in the class)
	zsh_auto_sugg_src="https://github.com/zsh-users/zsh-autosuggestions.git"
	zsh_auto_sugg_des="$HOME/.oh-my-zsh/custom/plugins/zsh-autosuggestions"
	zsh_syn_highli_src="https://github.com/zsh-users/zsh-syntax-highlighting.git"
	zsh_syn_highli_des="$HOME/.oh-my-zsh/custom/plugins/zsh-syntax-highlighting"

	echo "** Checking plugins: "
	sleep 1

	plugin_epx=$(grep "^plugins=(.*)$" "$HOME/.zshrc")
	plugin_vals=$(echo $plugin_epx | sed -E 's/plugins=\(|\)//g')

	echo -n " --> zsh-autosuggesions plugin: "
	sleep 0.5
	if ls $zsh_auto_sugg_des > /dev/null 2>&1; then
		echo "already installed."
	else
		echo -n "installing ..."
		git clone $zsh_auto_sugg_src $zsh_auto_sugg_des > /dev/null 2>&1
		echo "...done"

		plugin_vals="$plugin_vals zsh-autosuggestions"
	fi


	echo -n " --> zsh-syntax-highlighting plugin: "
	sleep 0.5
	if ls $zsh_syn_highli_des > /dev/null 2>&1; then
		echo "already installed."
	else
		echo -n "installing ..."
		git clone $zsh_syn_highli_src $zsh_syn_highli_des > /dev/null 2>&1
		echo "...done"

		plugin_vals="$plugin_vals zsh-syntax-highlighting"
	fi

	new_plugin_exp="plugins=($plugin_vals)"
	if [ "$plugin_epx" != "$new_plugin_exp" ]; then
		sed -i "s/$plugin_epx/$new_plugin_exp/" "$HOME/.zshrc"
		source "$HOME/.zshrc" > /dev/null 2>&1
	fi
	#END install plugins




	echo ""
	echo "*** Successfull, launch the ZSH command now ***"
	echo "-------------------------------------------------"

	exec zsh
else
	echo 'Sorry, you are not in group of sudoer'
	exit 1
fi