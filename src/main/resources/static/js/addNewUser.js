const form = document.forms["addNewUserForm"];
form.addEventListener('submit', addNewUser)

async function addNewUser(e) {
    e.preventDefault();
    let newUserRoles = [];
    for (let i = 0; i < form.roles.options.length; i++) {
        if (form.roles.options[i].selected) newUserRoles.push({
            id: form.roles.options[i].value,
            name: form.roles.options[i].name
        })
    }
    let response = await fetch("http://localhost:8080/api/users", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstName: form.firstName.value,
            lastName: form.lastName.value,
            age: form.age.value,
            email: form.email.value,
            password: form.password.value,
            roles: newUserRoles
        })
    })
    if (response.ok) {
        form.reset();
        allUsers();
        $('#allUsersTable').click();
    }
}
