$('#deleteModal').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showDeleteModal(id);
})

async function showDeleteModal(id) {
    let user = await getUser(id);
    let form = document.forms["deleteUser"];

    form.id.value = user.id;
    form.firstName.value = user.firstName;
    form.lastName.value = user.lastName;
    form.age.value = user.age;
    form.email.value = user.email;
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
    await deleteUser();
});

async function deleteUser() {
    const deleteForm = document.forms["deleteUser"];
    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault();
        fetch("http://localhost:8080/api/users/" + deleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(() => {
                allUsers();
                $('#deleteModal').modal('hide');
            })
    })
}