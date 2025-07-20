# Ansible

```
    sudo apt update
    sudo apt install software-properties-common
    sudo add-apt-repository --yes --update ppa:ansible/ansible
    sudo apt install ansible
```

## adhoc command syntax
    ansible -i inventory.ini machine-name -m ping
    ansible -i inventory.ini group-name -m ping
    ansible -i inventory.ini all -m ping
    ex: ansible -i inventory.ini localhost -m ping

    ansible -i inventory.ini all -m command -a "uptime"
    ansible -i inventory.ini all -m apt -a "name=nginx state=present" #absent =remove
    ansible -i inventory.ini all -m apt -a "name=nginx state=present" --become #sudo