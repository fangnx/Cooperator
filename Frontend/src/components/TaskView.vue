<!DOCTYPE html>
<template>
  <html lang="en">

  <head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.0/css/all.css" integrity="sha384-Mmxa0mLqhmOeaE8vgOSbKacftZcsNYDjQzuCOm6D02luYSzBG8vpaOykv9lFQ51Y" crossorigin="anonymous">
  </head>

  <body>

    <div class="container-fluid" id="top-container">
      <div class="container text-center">
        <div class="row">
          <div class="col-sm-6">
            <div class="container  text-left">
              <div @click="goToDashboard" style="display: inline-block;" id="img-container">
                <img src="https://user-images.githubusercontent.com/35735496/54735369-2f1d7b80-4b7c-11e9-93a2-505866f8ec69.png" width="300" height="100">
              </div>
            </div>
          </div>
          <div class="col-sm-6">
            <div class="row">
              <div class="col-sm-12">
                <button type="button" class="btn btn-primary" @click="goToAccount" id="Account-but" style="min-width: 100px; margin-right: 0px; margin-top: 35px">
                  <span class="glyphicon glyphicon-user"></span>
                  Account
                </button>
                <button type="button" class="btn btn-danger" @click="goToLogin" id="Logout-but" style="min-width: 100px; margin-left: 5px; margin-top: 35px">
                  Logout
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="container-fluid" id="title">
      <div class="container text-center">
        <div class="row">
          <div class="col-sm-12">
            <h1>{{task.name}}</h1>
          </div>
        </div>
      </div>
    </div>
    <br>

    <div class="container-fluid" id="course-title">
      <div class="container text-center">
        <div class="row">
          <div class="col-sm-12">
            <h3 style="text-align:left">
              Task Description
            </h3>
            <h4 class="task-description" style="text-align:left">
              {{task.description}}
            </h4>
          </div>
        </div>
      </div>
    </div>
    <br />
    <br />

    <div class="container-fluid" id="task-submission">
      <div class="row">
        <div class="col-sm-9">
          <ul class="nav nav-tabs">
            <li v-for="tab in tabs" :class="selectedTab === tab ? 'active' : ''" @click="selectedTab = tab">
              <a>
                <font size="4">{{ tab }}</font>
              </a>
            </li>
          </ul>
        </div>
        <ul class="nav navbar-nav navbar-right">
          <li>
            <button @click="showModal=true" :disabled="courseStatus !== 'ONGOING'" type="button" class="btn btn-success">
              <font size="4">
                Submit Document
              </font>
            </button>
          </li>
        </ul>
      </div>
      <br>

      <transition name="slide-fade" mode="out-in" appear>
        <div v-if="selectedTab === 'Task Information'" class="container-fluid" id="Submit Document-container">
          <div class="row">
            <div class="col-sm-12">
              <div class="card border-inverse mb-3">
                <div class="card-body">
                  <h3 class="card-title" style="margin-top:10px; margin-bottom:30px;">
                    Information
                  </h3>
                  <h4 style="color:gray"><em>Due Date</em></h4>
                  <h4><b>{{displayDate(task.dueDate)}}</b></h4>
                  <br>
                  <h4 style="color:gray"><em>Completion Status</em></h4>
                  <h4><b>{{taskStatus}}</b></h4>
                  <br>
                  <div v-if="instructionsInfo.length != 0">
                    <h4 style="color:gray"><em>Attached Instructions</em></h4>
                    <div v-for="s in instructionsInfo" @click="showDocInfo(s)">
                      <h4><a href="#">{{s.name}}</a></h4>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div v-if="selectedTab === 'Submission History'" key="info" class="container-fluid">
          <div class="row">
            <div class="col-sm-12">
              <table class="table table-striped table-bordered">
                <thead>
                  <tr>
                    <th scope="col" style="text-align:center; vertical-align:middle">
                      <h4>Submission</h4>
                    </th>
                    <th scope="col" style="text-align:center; vertical-align:middle">
                      <h4>Submission Date</h4>
                    </th>
                    <th scope="col" style="text-align:center; vertical-align:middle">
                      <h4>Submission Status</h4>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="s in submissionInfo">
                    <td style="text-align:center; vertical-align:middle">
                      <div @click="showDocInfo(s)">
                        <h5><a href="#">{{s.name}}</a></h5>
                      </div>
                    </td>
                    <td style="text-align:center; vertical-align:middle">
                      <h5>{{s.date}}</h5>
                    </td>
                    <td style="text-align:center; vertical-align:middle">
                      <h5>{{s.status}}</h5>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </transition>

      <!-- Submit Modal -->
      <transition name="modal" mode="out-in">
        <div v-if="showModal" key="submit">
          <div class="modal-mask">
            <div class="modal-wrapper" @click="showModal=false">


              <div class="modal-container" @click.stop>
                <div class="modal-header">
                  <slot name="header">
                    Document Submission
                  </slot>
                </div>
                <div class="modal-body">
                  <h4 style="text-align:left"><b>Document Name:</b></h4>
                  <input type="text" class="form-control form-control-lg" id="docName">
                  <p id="nameMsg"></p>
                  <br>
                  <h4 style="text-align:left"><b>Document:</b></h4>

                  <div class="custom-file">
                    <input type="file" @change="onFileSelected" class="custom-file-input" id="inputGroupFile01">
                    <label class="custom-file-label" for="inputGroupFile01">{{selectedFileName}}</label>
                  </div>
                  <br>
                  <br>
                </div>
                <div style="text-align:center">
                  <slot>
                    <button class="btn btn-primary" style="min-width:120px" @click="checkSubmitDocument">
                      <font size="3">Submit</font>
                    </button>
                  </slot>
                </div>
              </div>

            </div>
          </div>
        </div>
      </transition>

      <!-- Processing Modal -->
      <transition name="modal" mode="out-in">
        <div v-if="showModalProcessing" key="processing">
          <div class="modal-mask">
            <div class="modal-wrapper" @click="showModalProcessing=false">
              <div class="modal-container" @click.stop>
                <div class="modal-header">
                  <slot name="header">
                    Processing Registration
                  </slot>
                </div>
                <div class="modal-body">
                  <h4 style="text-align:center">Uploading document.</h4>
                  <br>
                  <div style="text-align:center">
                    <img src="https://user-images.githubusercontent.com/22506116/54891974-f9cba300-4e85-11e9-8842-9ab32c10658f.gif" width="200" height="200">
                  </div>
                  <br>
                </div>
                <div style="text-align:center">
                  <slot>
                    <br>
                    <br>
                  </slot>
                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>

      <!-- Warning Modal -->
      <transition name="modal" mode="out-in">
        <div v-if="showModalWarning" key="confirm">
          <div class="modal-mask">
            <div class="modal-wrapper" @click="showModalWarning=false">
              <div class="modal-container" @click.stop>
                <div class="modal-header" id="warning-header">
                  <slot name="header">
                    Warning
                  </slot>
                </div>
                <div class="modal-body">
                  <h4 style="text-align:center">A document with the name <b>{{this.existingDocName}}</b> already exists.</h4>
                  <h4 style="text-align:center">This action will overwrite it.</h4>
                  <h4 style="text-align:center">Do you want to continue?</h4>
                </div>
                <div style="text-align:center">
                  <slot>
                    <br>
                    <br>
                    <button class="btn btn-primary" style="min-width:120px" @click="submitDocument()">
                      <font size="3">Yes</font>
                    </button>
                    <button class="btn btn-secondary" style="min-width:120px" @click="showModalWarning=false">
                      <font size="3">No</font>
                    </button>
                  </slot>
                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>

      <!-- Success Modal -->
      <transition name="modal" mode="out-in">
        <div v-if="showModalSuccess" key="success">
          <div class="modal-mask">
            <div class="modal-wrapper" @click="showModalSuccess=false">
              <div class="modal-container" @click.stop>
                <div class="modal-header">
                  <slot name="header">
                    Submission Successful
                  </slot>
                </div>
                <div class="modal-body">
                  <br>
                </div>
                <div style="text-align:center">
                  <slot>
                    <button class="btn btn-primary" style="min-width:120px" @click="showModalSuccess=false">
                      <font size="3">Done</font>
                    </button>
                  </slot>
                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>

      <!-- URL Display Modal -->
      <transition name="modal" mode="out-in">
        <div v-if="showModalURL" key="urlModal">
          <div class="modal-mask">
            <div class="modal-wrapper" @click="showModalURL=false">
              <div class="modal-container" @click.stop>
                <div class="modal-header">
                  <slot name="header">
                    Uploaded Information
                  </slot>
                </div>
                <div class="modal-body">
                  <h4 style="color:gray"><em>Document Name</em></h4>
                  <h4><b>{{displayDocName}}</b></h4>
                  <br>
                  <h4 style="color:gray"><em>Download Link</em></h4>
                  <h4 style="word-wrap: break-word"><b><a href="" id="link2" target="_blank" @click="redirectTo()">Download</a> </b></h4>
                  <br>
                </div>
                <br>
                <div style="text-align:center">
                  <slot>
                    <button class="btn btn-primary" style="min-width:120px" @click="showModalURL=false">
                      <font size="3">Done</font>
                    </button>
                  </slot>
                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>

    </div>

  </body>

  </html>
</template>

<style>
  #img-container:hover img {
    opacity: 0.8;
  }

  #top-container {
    margin-bottom: 0;
    background-color: #333335;
    color: #ffffff;
  }

  #top-container h2 {
    text-align: left;
    margin-top: 30px;
    margin-bottom: 20px;
  }

  #top-container a {
    display: inline-block;
    margin-top: 35px;
  }

  #logo-link {
    margin-bottom: 0px;
  }

  #Account-but {
    margin-left: 100px;
    min-width: 0%;
  }

  #Logout-but {
    margin-left: 50px;
    min-width: 50%;
  }

  #title h1 {
    text-align: left;
    margin-top: 30px;
    margin-bottom: 0px;
  }

  #nav-bar {
    margin-top: 25px;
    margin-bottom: 40px;
  }

  .task-card {
    box-shadow: 0 6px 12px 0 rgba(0, 0, 0, 0.2);
    width: 66%;
    height: auto;
    margin: auto;
    text-align: left;
  }

  .task-description {
    font-style: italic;
    font-weight: 400;
  }

  #task-submission {
    width: 80%;
  }

  .modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
    transition: opacity .3s ease;
  }

  .modal-wrapper {
    display: table-cell;
    vertical-align: middle;
  }

  .modal-container {
    width: 400px;
    margin: 0px auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
    transition: all .3s ease;
    font-family: Helvetica, Arial, sans-serif;
  }

  .modal-header {
    margin-top: 0;
    font-size: 24px;
  }

  .modal-body {
    margin: 0;
  }

  .modal-default-button {
    float: right;
  }

  .modal-enter {
    opacity: 0;
  }

  .modal-leave-active {
    opacity: 0;
    transition: all .3s ease;
  }

  /*.modal-enter-active {
    opacity: 0;
    transition: all .3s ease;
    }*/

    .modal-enter .modal-container,
    .modal-leave-active .modal-container,
    .modal-leave-to .modal-container {
      /*-webkit-transform: scale(1.1);*/
      transform: translateY(100px);
    }

    /* Tab transition animations */
    .slide-fade-enter-active {
      transition: all .3s ease;
    }

    .slide-fade-leave-active {
      transition: all .3s ease;
    }

    .slide-fade-enter,
    .slide-fade-leave-to {
      transform: translateX(10px);
      opacity: 0;
    }
  </style>

  <script>
    import Router from 'vue-router'
    import axios from 'axios'
    var moment = require('moment')
    var config = require('../../config')
    /* AXIOS object configuration */
    var frontendUrl = 'https://' + config.dev.hoiust + ':' + config.dev.port
  var backendUrl = 'https://' + config.dev.backendHost //+ ':' + config.dev.backendPort

  var AXIOS = axios.create({
    baseURL: backendUrl
  })

  export default {
    name: 'courseinfo',
    data() {
      return {
        selectedFile: null,
        selectedFileName: 'Choose File',
        docURL: null,
        AcceptanceFormURL: null,
        showModal: false,
        showModalSuccess: false,
        showModalProcessing: false,
        showModalURL: false,
        existingDocName: null,
        showModalWarning: false,
        tabs: ['Task Information', 'Submission History'],
        selectedTab: 'Task Information',
        task: null,
        documents: null,
        statusDisplay: {
          COMPLETED: 'Completed',
          INCOMPLETE: 'Incomplete',
          LATE_COMPLETED: 'Late Completed'
        },
        displayDocName: null,
        displayURLName: null,
        courseStatus: null,
        inputName: null
      }
    },
    created() {
      AXIOS.get(`/tasks/` + this.$route.params.id)
      .then(response => {
        this.task = response.data;
      })
      .catch(e => {
        var errorMsg = e.message;
        console.log(errorMsg);
        this.errorPerson = errorMsg;
      })
      AXIOS.get(`/tasks/` + this.$route.params.id + `/documents`)
      .then(response => {
        this.documents = response.data._embedded.documents;
      })
      .catch(e => {
        var errorMsg = e.message;
        console.log(errorMsg);
        this.errorPerson = errorMsg;
      })
      AXIOS.get(`/studentEnrollments/` + this.$route.params.enrollmentID)
      .then(response => {
        this.courseStatus = response.data.status
      })
      .catch(e => {
        var errorMsg = e.message;
        console.log(errorMsg);
        this.errorPerson = errorMsg;
      })
    },

    methods: {
      goToDashboard: function() {
        var studentID = this.$route.params.enrollmentID.split('-').shift()
        this.$router.push({
          name: 'Dashboard',
          params: {
            id: studentID
          }
        })
      },
      goToLogin: function() {
        this.$router.push({
          name: 'Login',
        })
      },
      goToAccount: function() {
        var studentID = this.$route.params.enrollmentID.split('-').shift()
        this.$router.push({
          name: 'StudentInformation',
          params: {
            id: studentID
          }
        })
      },
      displayDate: function(date) {
        return moment(date).format("MMM Do, YYYY")
      },
      showDocInfo: function(s) {
        this.displayDocName = s.name
        this.displayURLName = s.url
        this.showModalURL = true
      },

      /* Method to update the selected file */
      onFileSelected(event) {
        this.selectedFile = event.target.files[0]
        this.selectedFileName = this.selectedFile.name
        console.log(event.target.files[0])
      },

      /* Method to upload the selected file to firebase */
      async onUpload() {
        // Set up the file
        const fd = new FormData();
        var id = Math.floor((Math.random() * 10000000));
        var filename = this.selectedFile.name;
        var nameNoExt = filename.split('.',2)[0];
        var ext = filename.split('.',2)[1];
        filename = nameNoExt+id+'.'+ext; //add id to the file name

        fd.append('doc', this.selectedFile, filename)
        // Upload the file to Firebase
        try{
          let response = await axios.post('https://us-central1-cooperator-2b466.cloudfunctions.net/uploadFile', fd, {
            onUploadProgress: uploadEvent => {
              console.log('upload pregress: ' + Math.floor(uploadEvent.loaded / uploadEvent.total) * 100)
            }
          })
          this.docURL = response.data.shortURL
          console.log(this.docURL)
          return response;

        }catch(e){
          var errorMsg = e.message;
          console.log(errorMsg);
          this.errorPerson = errorMsg;
        }
      },

      /* Method to set the download url for a file */
      redirectTo() {
        document.getElementById("link2").setAttribute("href", this.displayURLName)
      },

      /* Method to check if a document is valid and/or should be overwritten */
      checkSubmitDocument: async function() {
        var valid = true;
        var inputName = document.getElementById('docName').value
        var inputDoc = document.getElementById('inputGroupFile01').value

        // Check the input name
        if (inputName === '' || inputName === null) {
          valid = false
          document.getElementById('nameMsg').innerHTML = 'Please Enter A Valid Document Name'
          document.getElementById('nameMsg').style.color = 'red'
          document.getElementById("docName").className = 'form-control form-control-lg is-invalid'
        } else {
          valid = true
          document.getElementById('nameMsg').innerHTML = ''
          document.getElementById('nameMsg').style.color = ''
          document.getElementById("docName").className = 'form-control form-control-lg'
        }
        // Check the input file
        if (inputDoc === null || inputDoc === '') {
          valid = false
          document.getElementById('inputGroupFile01').className = 'custom-file-input is-invalid'
        } else {
          valid = valid ? true : false
          document.getElementById('inputGroupFile01').className = 'custom-file-input form-control-lg'
        }

        // Check if a student has a doc with the same name for the same task
        if (valid) {
          this.inputName = inputName
          var existingDocName = null
          for (var i in this.documents) {
            if (this.documents[i].name === inputName) {
              existingDocName = this.documents[i].name
              break
            }
          }
          this.selectedFileName = 'Choose File'
          // If no doc woth the same name was found, submit the doc. Otherwise, show averwrite modal
          if (existingDocName === null) {
            this.submitDocument()
          } else {
            this.existingDocName = existingDocName
            this.showModal = false
            this.showModalWarning = true
          }
        }
      },

      /* Method to submit a document */
      submitDocument: async function() {
        this.showModal = false
        this.showModalWarning = false
        this.showModalProcessing = true

        var inputName = this.inputName
        // upload the file to firebase
        try{
          let wait = await this.onUpload()
        }catch(e){
          var errorMsg = e.message;
          console.log(errorMsg);
          this.errorPerson = errorMsg;
        }
        // Upload the file to heroku DB
        AXIOS.post(`/document?studentEnrollmentID=` + this.$route.params.enrollmentID + `&taskName=` + this.task.name, {
          "name": inputName,
          "url": this.docURL
        })
        // Update the tasks and documents data
        .then(response => {
          AXIOS.get(`/tasks/` + this.$route.params.id + `/documents`)
          .then(response => {
            this.documents = response.data._embedded.documents;

            AXIOS.get(`/tasks/` + this.$route.params.id)
            .then(response => {
              this.task = response.data;
              this.showModalProcessing = false;
              this.showModalSuccess = true; // Show success modal
            })
            .catch(e => {
              var errorMsg = e.message;
              console.log(errorMsg);
              this.errorPerson = errorMsg;
            })

          })
          .catch(e => {
            var errorMsg = e.message;
            console.log(errorMsg);
            this.errorPerson = errorMsg;
          })
        })
        .catch(e => {
          var errorMsg = e.message;
          console.log(errorMsg);
          this.errorPerson = errorMsg;
        })
      }
    },
    computed: {
      submissionInfo: function() {
        console.log(this.documents)
        var ret = []
        var currDate = moment(this.task.dueDate)
        for (var i in this.documents) {
          if (this.documents[i].name.search(/INSTRUCTIONS_/) == -1) {
            var submissionDate = moment(this.documents[i].submissionDate)
            ret.push({
              name: this.documents[i].name,
              date: moment(this.documents[i].submissionDate).format('MMM Do, YYYY'),
              status: moment.duration(currDate.diff(submissionDate)).asDays() >= 0 ? 'On Time' : 'Late',
              url: this.documents[i].url
            })
          }
        }
        return ret
      },
      instructionsInfo: function() {
        console.log(this.documents)
        var ret = []
        var currDate = moment(this.task.dueDate)
        for (var i in this.documents) {
          if (this.documents[i].name.search(/INSTRUCTIONS_/) != -1) {
            var submissionDate = moment(this.documents[i].submissionDate)
            ret.push({
              name: this.documents[i].name.replace(/INSTRUCTIONS_/, ''),
              date: moment(this.documents[i].submissionDate).format('MMM Do, YYYY'),
              status: moment.duration(currDate.diff(submissionDate)).asDays() >= 0 ? 'On Time' : 'Late',
              url: this.documents[i].url
            })
          }
        }
        return ret
      },
      taskStatus: function() {
        return this.statusDisplay[this.task.taskStatus]
      }
    }
  }
</script>
