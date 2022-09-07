$('#editModal').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showEditModal(id);
})

async function showEditModal(id) {
    let user = await getUser(id);
    let form = document.forms["editUser"];

    form.id.value = user.id;
    form.firstName.value = user.firstName;
    form.lastName.value = user.lastName;
    form.age.value = user.age;
    form.email.value = user.email;
    form.password.value = "";
    form.roles.options[0].selected = false;
    form.roles.options[1].selected = false;
    for (let i = 0; i < 2; i++) {
        for (let j = 0; j < 2; j++) {
            if (user.roles[i].id == form.roles.options[j].value) {
                form.roles.options[j].selected = true;
            }
        }
    }
}

$(async function () {
    await editUser();
});

function editUser() {
    const editForm = document.forms["editUser"];
    editForm.addEventListener("submit", ev => {
        ev.preventDefault();
        let editUserRoles = [];
        for (let i = 0; i < editForm.roles.options.length; i++) {
            if (editForm.roles.options[i].selected) editUserRoles.push({
                id: editForm.roles.options[i].value,
                name: editForm.roles.options[i].name
            })
        }

        fetch("http://localhost:8080/api/users/", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editForm.id.value,
                firstName: editForm.firstName.value,
                lastName: editForm.lastName.value,
                age: editForm.age.value,
                email: editForm.email.value,
                password: editForm.password.value,
                roles: editUserRoles
            })
        }).then(() => {
            allUsers();
            $('#editModal').modal('hide');
        })
    })
}
